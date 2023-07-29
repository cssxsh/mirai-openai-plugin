package xyz.cssxsh.mirai.openai

import io.ktor.client.call.*
import io.ktor.client.network.sockets.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.cio.*
import io.ktor.utils.io.*
import kotlinx.coroutines.*
import net.mamoe.mirai.console.command.CommandSender.Companion.toCommandSender
import net.mamoe.mirai.console.permission.*
import net.mamoe.mirai.console.permission.PermissionService.Companion.hasPermission
import net.mamoe.mirai.console.permission.PermitteeId.Companion.permitteeId
import net.mamoe.mirai.contact.*
import net.mamoe.mirai.contact.Contact.Companion.uploadImage
import net.mamoe.mirai.event.*
import net.mamoe.mirai.event.events.*
import net.mamoe.mirai.message.*
import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.message.data.MessageSource.Key.quote
import net.mamoe.mirai.utils.*
import xyz.cssxsh.mirai.openai.config.*
import xyz.cssxsh.mirai.openai.data.*
import xyz.cssxsh.openai.*
import xyz.cssxsh.openai.image.*
import java.io.*
import java.util.*
import kotlin.coroutines.*

@PublishedApi
internal object MiraiOpenAiListener : SimpleListenerHost() {
    private val client = OpenAiClient(config = MiraiOpenAiConfig)
    private val folder = File(MiraiOpenAiConfig.folder)
    private val logger = MiraiLogger.Factory.create(this::class)
    private val lock: MutableMap<Long, MessageEvent> = java.util.concurrent.ConcurrentHashMap()
    internal val completion: Permission by MiraiOpenAiPermissions
    internal val image: Permission by MiraiOpenAiPermissions
    internal val chat: Permission by MiraiOpenAiPermissions
    internal val question: Permission by MiraiOpenAiPermissions
    internal val reload: Permission by MiraiOpenAiPermissions
    internal val economy: Permission by MiraiOpenAiPermissions

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        when (exception) {
            is kotlinx.coroutines.CancellationException -> {
                // ...
            }
            is ExceptionInEventHandlerException -> {
                logger.warning({ "MiraiOpenAiListener with ${exception.event}" }, exception.cause)
                val event = exception.event as? MessageEvent ?: return
                lock.remove(event.sender.id, event)
                if (MiraiOpenAiConfig.reply) launch {
                    when (val cause = exception.cause) {
                        is SocketTimeoutException, is ConnectTimeoutException -> {
                            event.subject.sendMessage(event.message.quote() + "OpenAI API 超时 请重试")
                        }
                        is OverFileSizeMaxException -> {
                            event.subject.sendMessage(event.message.quote() + "OpenAI API 生成图片过大, 请重试")
                        }
                        is OpenAiException -> {
                            if (MiraiOpenAiConfig.exception) {
                                event.subject.sendMessage(event.message.quote() + "OpenAI API 异常, ${cause.message}")
                            }
                        }
                        is IllegalStateException -> {
                            val info = cause.message
                            when {
                                info == null -> Unit
                                info.endsWith(", failed on all servers.") -> {
                                    event.subject.sendMessage(event.message.quote() + "OpenAI API 生成图片上传失败, 请重试")
                                }
                            }
                        }
                        is NoTransformationFoundException -> {
                            val info = cause.message
                            when {
                                info == null -> Unit
                                "cloudflare" in info -> {
                                    event.subject.sendMessage(event.message.quote() + "OpenAI API 访问失败, 请检查代理设置")
                                }
                            }
                        }
                    }
                }
            }
            else -> Unit
        }
    }

    @EventHandler
    suspend fun MessageEvent.handle() {
        if (sender.id in lock) return
        val content = message.contentToString()
        when {
            content.startsWith(MiraiOpenAiConfig.completion)
                && (MiraiOpenAiConfig.permission.not() || toCommandSender().hasPermission(completion))
            -> test(event = this)
            content.startsWith(MiraiOpenAiConfig.image)
                && (MiraiOpenAiConfig.permission.not() || toCommandSender().hasPermission(image))
            -> image(event = this)
            content.startsWith(MiraiOpenAiConfig.chat)
                && (MiraiOpenAiConfig.permission.not() || toCommandSender().hasPermission(chat))
            -> chat(event = this)
            content.startsWith(MiraiOpenAiConfig.question)
                && (MiraiOpenAiConfig.permission.not() || toCommandSender().hasPermission(question))
            -> question(event = this)
            content.startsWith(MiraiOpenAiConfig.reload)
                && (MiraiOpenAiConfig.permission.not() || toCommandSender().hasPermission(reload))
            -> with(MiraiOpenAiPlugin) {
                config.forEach { config ->
                    config.reload()
                }
                client.clearToken()
                subject.sendMessage( "OPENAI 配置已重载")
            }
            message.findIsInstance<At>()?.target == bot.id && MiraiOpenAiConfig.chatByAt
                && (MiraiOpenAiConfig.permission.not() || toCommandSender().hasPermission(chat))
            -> chat(event = this)
            else -> return
        }
    }

    private suspend fun test(event: MessageEvent) {
        if (lock.size >= MiraiOpenAiConfig.limit) {
            launch {
                event.subject.sendMessage("聊天服务已开启过多，请稍后重试".toPlainText())
            }
            return
        }
        val prompt = event.message.contentToString()
            .removePrefix(MiraiOpenAiConfig.completion)

        logger.verbose { "prompt: $prompt" }

        val completion = client.completion.create(model = "text-davinci-003") {
            prompt(prompt)
            user(event.senderName)
            CompletionConfig.push(this)
        }

        event.subject.sendMessage(buildMessageChain {
            add(event.message.quote())
            append("Usage: ").append(completion.usage.totalTokens.toString()).append('\n')
            for (choice in completion.choices) {
                append("Choice.").append(choice.index.toString()).append(" FinishReason: ").append(choice.finishReason)
                if (choice.text.firstOrNull() != '\n') append('\n')
                append(choice.text).append('\n')
            }
        })
    }

    private suspend fun image(event: MessageEvent) {
        if (lock.size >= MiraiOpenAiConfig.limit) {
            launch {
                event.subject.sendMessage("聊天服务已开启过多，请稍后重试".toPlainText())
            }
            return
        }
        val prompt = event.message.contentToString()
            .removePrefix(MiraiOpenAiConfig.image)

        logger.verbose { "prompt: $prompt" }

        val result = client.image.create(prompt = prompt) {
            user(event.senderName)
            ImageConfig.push(this)
        }

        event.subject.sendMessage(buildMessageChain {
            add(event.message.quote())
            for (item in result.data) {
                val file = store(item = item, folder = folder)
                val image = event.subject.uploadImage(file)

                add(image)
            }
        })
    }

    private suspend fun chat(event: MessageEvent) {
        if (lock.size >= MiraiOpenAiConfig.limit) {
            launch {
                event.subject.sendMessage("聊天服务已开启过多，请稍后重试".toPlainText())
            }
            return
        }
        if (MiraiOpenAiTokensData.balance(event.sender) < 0) {
            launch {
                event.subject.sendMessage(buildMessageChain {
                    appendLine("你的 Tokens 不足")
                    append(At(event.sender))
                })
            }
            return
        }
        val system = event.message
            .findIsInstance<PlainText>()?.content.orEmpty()
            .removePrefix(MiraiOpenAiConfig.chat)
            .replace("""#(<.+?>|\S+)""".toRegex()) { match ->
                val (path) = match.destructured
                try {
                    MiraiOpenAiPrompts.prompt(path = path.removeSurrounding("<", ">"))
                } catch (exception: FileNotFoundException) {
                    logger.warning({ "文件不存在" }, exception)
                    match.value
                }
            }
            .replace("""[~.]\s+""".toRegex()) { _ -> MiraiOpenAiPrompts.prompt(event.sender.id, event.subject.id) }
            .ifBlank { MiraiOpenAiPrompts.prompt(event.sender.id, event.subject.id) }
        lock[event.sender.id] = event
        val buffer = mutableListOf<ChoiceMessage>()
        buffer.add(ChoiceMessage(role = "system", content = system))
        val message = if (MiraiOpenAiConfig.atOnce) {
            send(event = event, buffer = buffer)
        } else {
            MiraiOpenAiConfig.chatStart.ifEmpty { "聊天将开始" }
        }
        launch {
            event.subject.sendMessage(event.message.quote() + message)
        }
        launch {
            while (isActive) {
                val next = event.nextMessage(ChatConfig.timeout, EventPriority.HIGH, intercept = true) {
                    val text = it.message.contentToString()
                    when {
                        text == MiraiOpenAiConfig.stop -> true
                        MiraiOpenAiConfig.prefix -> text.startsWith(MiraiOpenAiConfig.chat) ||
                            (it.message.findIsInstance<At>()?.target == event.bot.id && MiraiOpenAiConfig.chatByAt)
                        else -> true
                    }
                }
                val content = if (MiraiOpenAiConfig.prefix) {
                    next.contentToString().removePrefix(MiraiOpenAiConfig.chat)
                } else {
                    next.contentToString()
                }
                if (content == MiraiOpenAiConfig.stop) break

                buffer.add(ChoiceMessage(role = "user", content = content))

                val reply = send(event = event, buffer = buffer)
                launch {
                    event.subject.sendMessage(next.quote() + reply)
                }
            }
        }.invokeOnCompletion { cause ->
            lock.remove(event.sender.id, event)
            when (cause) {
                null -> Unit
                is TimeoutCancellationException -> logger.info { "聊天已终止 ${event.sender}" }
                else -> handleException(coroutineContext, ExceptionInEventHandlerException(event, cause = cause))
            }
            if (MiraiOpenAiConfig.bye) {
                launch {
                    event.subject.sendMessage(event.message.quote() + "聊天已终止")
                }
            }
        }
    }

    private suspend fun send(event: MessageEvent, buffer: MutableList<ChoiceMessage>): String {
        val chat = client.chat.create(model = "gpt-3.5-turbo") {
            messages(buffer)
            user(event.senderName)
            ChatConfig.push(this)
        }
        logger.debug { "${chat.model} - ${chat.usage}" }
        launch {
            MiraiOpenAiTokensData.minusAssign(event.sender, chat.usage.totalTokens)
        }

        val reply = chat.choices.first()
        val message = reply.message ?: ChoiceMessage(
            role = "assistant",
            content = reply.text
        )
        buffer.add(message)
        if (chat.usage.totalTokens > ChatConfig.maxTokens * 0.96) {
            if (buffer.size > 2) buffer.removeAt(1)
        }

        return when (reply.finishReason) {
            "length" -> {
                logger.warning { "max_tokens not enough for ${event.sender} " }
                message.content.orEmpty().trimStart().ifEmpty { "..." }
            }
            "function_call" -> {
                TODO("function_call")
            }
            else -> {
                message.content.orEmpty().trimStart().ifEmpty { "..." }
            }
        }
    }

    private suspend fun question(event: MessageEvent) {
        if (lock.size >= MiraiOpenAiConfig.limit) {
            launch {
                event.subject.sendMessage("聊天服务已开启过多，请稍后重试".toPlainText())
            }
            return
        }
        if (MiraiOpenAiTokensData.balance(event.sender) < 0) {
            launch {
                event.subject.sendMessage(buildMessageChain {
                    appendLine("你的 Tokens 不足")
                    append(At(event.sender))
                })
            }
            return
        }
        val prompt = event.message.contentToString()
            .removePrefix(MiraiOpenAiConfig.question)
        lock[event.sender.id] = event
        val buffer = StringBuffer()
        val message = if (MiraiOpenAiConfig.atOnce) {
            buffer.append("Q: ").append(prompt).append('\n')
            buffer.append("A: ")
            send(event = event, buffer = buffer)
        } else {
            buffer.append(prompt)
            buffer.append('\n')
            MiraiOpenAiConfig.questionStart.ifEmpty { "问答将开始" }
        }
        launch {
            event.subject.sendMessage(event.message.quote() + message)
        }
        launch {
            while (isActive) {
                val next = event.nextMessage(QuestionConfig.timeout, EventPriority.HIGH, intercept = true) {
                    val text = it.message.contentToString()
                    when {
                        text == MiraiOpenAiConfig.stop -> true
                        MiraiOpenAiConfig.prefix -> text.startsWith(MiraiOpenAiConfig.question)
                        else -> true
                    }
                }
                val content = if (MiraiOpenAiConfig.prefix) {
                    next.contentToString().removePrefix(MiraiOpenAiConfig.question)
                } else {
                    next.contentToString()
                }
                if (content == MiraiOpenAiConfig.stop) break

                if (buffer.length > QuestionConfig.maxTokens * 2) {
                    val first = buffer.indexOf("Q: ")
                    val second = buffer.indexOf("Q: ", first)
                    buffer.delete(first, second)
                }

                buffer.append("Q: ").append(content).append('\n')
                buffer.append("A: ")

                logger.verbose { "prompt: $buffer" }

                val reply = send(event = event, buffer = buffer)
                launch {
                    event.subject.sendMessage(next.quote() + reply)
                }
            }
        }.invokeOnCompletion { cause ->
            lock.remove(event.sender.id, event)
            when (cause) {
                null -> Unit
                is TimeoutCancellationException -> logger.info { "问答已终止 ${event.sender}" }
                else -> handleException(coroutineContext, ExceptionInEventHandlerException(event, cause = cause))
            }
            if (MiraiOpenAiConfig.bye) {
                launch {
                    event.subject.sendMessage(event.message.quote() + "问答已终止")
                }
            }
        }
    }

    private suspend fun send(event: MessageEvent, buffer: StringBuffer): String {
        val completion = client.completion.create(model = "text-davinci-003") {
            prompt(buffer.toString())
            user(event.senderName)
            QuestionConfig.push(this)
            stop("\n")
        }
        logger.debug { "${completion.model} - ${completion.usage}" }
        launch {
            MiraiOpenAiTokensData.minusAssign(event.sender, completion.usage.totalTokens)
        }

        val reply = completion.choices.first()
        buffer.append(reply.text).append('\n')

        when (reply.finishReason) {
            "length" -> logger.warning { "max_tokens not enough for ${event.sender} " }
            else -> Unit
        }

        return reply.text.trimStart().ifEmpty { "..." }
    }

    private suspend fun store(item: ImageInfo.Data, folder: File): File {
        return when {
            item.url.isNotEmpty() -> {
                val response = client.http.get(item.url)
                val filename = response.headers[HttpHeaders.ContentDisposition]
                    ?.let { ContentDisposition.parse(it).parameter(ContentDisposition.Parameters.FileName) }
                    ?: "${UUID.randomUUID()}.${response.contentType()?.contentSubtype ?: "png"}"

                val target = folder.resolve(filename)
                response.bodyAsChannel().copyAndClose(target.writeChannel())
                // XXX: target.writeChannel() 不堵塞 https://github.com/cssxsh/mirai-openai-plugin/issues/2
                delay(3_000)
                target
            }
            item.base64.isNotEmpty() -> {
                val bytes = Base64.getDecoder().decode(item.base64)

                val target = folder.resolve("${UUID.randomUUID()}.png")
                target.writeBytes(bytes)

                target
            }
            else -> throw IllegalArgumentException("data is empty")
        }
    }

    @EventHandler
    fun GroupMessageEvent.economy() {
        if (MiraiOpenAiTokensData.economy.not()) return
        if (MiraiOpenAiConfig.permission.not() && sender.isOperator().not()) return
        if (MiraiOpenAiConfig.permission && toCommandSender().hasPermission(economy).not()) return
        val match = """${MiraiOpenAiConfig.tokens}\s*(-?\d+)""".toRegex().find(message.contentToString()) ?: return
        val (quantity) = match.destructured
        val user = message.firstIsInstanceOrNull<At>()?.let { group[it.target] }
        launch {
            if (user != null) {
                MiraiOpenAiTokensData.plusAssign(user, quantity.toInt())
                val balance = MiraiOpenAiTokensData.balance(user)
                group.sendMessage(buildMessageChain {
                    appendLine("你拥有了 $balance OpenAiTokens")
                    append(At(user))
                })
            } else {
                for (member in group.members) {
                    MiraiOpenAiTokensData.plusAssign(member, quantity.toInt())
                }
                group.sendMessage(buildMessageChain {
                    appendLine("你们拥有了 $quantity OpenAiTokens")
                    append(AtAll)
                })
            }
        }
    }

    @EventHandler
    fun SignEvent.economy() {
        if (MiraiOpenAiTokensData.economy.not()) return
        val member = user as? NormalMember ?: return
        if (MiraiOpenAiConfig.permission && member.permitteeId.hasPermission(chat).not()) return
        MiraiOpenAiTokensData.plusAssign(member, EconomyConfig.sign)
        launch {
            member.group.sendMessage(buildMessageChain {
                appendLine("你获得了 ${EconomyConfig.sign} OpenAiTokens")
                append(At(user))
            })
        }
    }

    @EventHandler
    fun MessageEvent.bind() {
        if (MiraiOpenAiConfig.permission && toCommandSender().hasPermission(chat).not()) return
        val content = message.contentToString()
        if (content.startsWith(MiraiOpenAiConfig.bind).not()) return
        val path = content
            .removePrefix(MiraiOpenAiConfig.bind)
            .trim()
        val target = when {
            (sender as? NormalMember)?.isOperator() ?: false -> (sender as NormalMember).group
            else -> sender
        }
        val prompt = try {
            if (path == "default") {
                MiraiOpenAiPrompts.unbind(id = target.id)
            } else {
                MiraiOpenAiPrompts.bind(id = target.id, path = path)
            }
        } catch (_: FileNotFoundException) {
            launch {
                subject.sendMessage("文件不存在")
            }
            return
        }
        launch {
            subject.sendMessage(buildMessageChain {
                if (target is Group) {
                    appendLine("将为群绑定 $path")
                } else {
                    appendLine("将为你绑定 $path")
                }
                appendLine()
                appendLine(prompt)
            })
        }
    }

    @EventHandler
    fun MessageEvent.prompts() {
        if (MiraiOpenAiConfig.permission && toCommandSender().hasPermission(chat).not()) return
        val content = message.contentToString()
        if (content.startsWith(MiraiOpenAiConfig.prompts).not()) return
        val index = """\d+""".toRegex().find(content)?.value?.toInt() ?: 1
        val files = MiraiOpenAiPrompts.files()
        val page = files.chunked(MiraiOpenAiConfig.page).getOrNull(index - 1).orEmpty()

        launch {
            subject.sendMessage(buildMessageChain {
                appendLine("共 ${files.size} 个, 第 $index 页 ")
                appendLine()
                page.forEach { name ->
                    appendLine(name)
                }
            })
        }
    }
}