package xyz.cssxsh.mirai.openai

import io.ktor.client.network.sockets.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.cio.*
import io.ktor.utils.io.*
import kotlinx.coroutines.*
import net.mamoe.mirai.contact.Contact.Companion.uploadImage
import net.mamoe.mirai.event.*
import net.mamoe.mirai.event.events.*
import net.mamoe.mirai.message.*
import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.message.data.MessageSource.Key.quote
import net.mamoe.mirai.utils.*
import xyz.cssxsh.mirai.openai.config.*
import xyz.cssxsh.openai.*
import xyz.cssxsh.openai.image.*
import java.io.File
import java.util.Base64
import java.util.UUID
import kotlin.coroutines.*

@PublishedApi
internal object MiraiOpenAiListener : SimpleListenerHost() {
    private val client = OpenAiClient(config = MiraiOpenAiConfig)
    private val folder = File(MiraiOpenAiConfig.folder)
    private val logger = MiraiLogger.Factory.create(this::class)
    private val lock: MutableMap<Long, MessageEvent> = java.util.concurrent.ConcurrentHashMap()

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        when (exception) {
            is kotlinx.coroutines.CancellationException -> {
                // ...
            }
            is ExceptionInEventHandlerException -> {
                logger.warning({ "MiraiOpenAiListener with ${exception.event}" }, exception.cause)
                if (MiraiOpenAiConfig.reply && exception.event is MessageEvent) launch {
                    val event = exception.event as MessageEvent
                    when (exception.cause) {
                        is SocketTimeoutException, is ConnectTimeoutException -> {
                            event.subject.sendMessage(event.message.quote() + "OpenAI API 超时 请重试")
                        }
                        is OverFileSizeMaxException -> {
                            event.subject.sendMessage(event.message.quote() + "OpenAI API 图片过大 请重试")
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
        val message: Message = when {
            content.startsWith(MiraiOpenAiConfig.completion) -> completion(event = this)
            content.startsWith(MiraiOpenAiConfig.image) -> image(event = this)
            content.startsWith(MiraiOpenAiConfig.chat) -> chat(event = this)
            content.startsWith(MiraiOpenAiConfig.question) -> question(event = this)
            else -> return
        }

        subject.sendMessage(message)
    }

    private suspend fun completion(event: MessageEvent): Message {
        val prompt = event.message.contentToString()
            .removePrefix(MiraiOpenAiConfig.completion)

        logger.verbose { "prompt: $prompt" }

        val completion = client.completion.create(model = "text-davinci-003") {
            prompt(prompt)
            user(event.senderName)
            CompletionConfig.push(this)
        }
        logger.debug { "${completion.model} - ${completion.usage}" }
        return buildMessageChain {
            add(event.message.quote())
            for (choice in completion.choices) {
                append("Choice.").append(choice.index.toString()).append(" FinishReason: ").append(choice.finishReason)
                if (choice.text.firstOrNull() != '\n') append('\n')
                append(choice.text).append('\n')
            }
        }
    }

    private suspend fun image(event: MessageEvent): Message {
        val prompt = event.message.contentToString()
            .removePrefix(MiraiOpenAiConfig.image)

        logger.verbose { "prompt: $prompt" }

        val result = client.image.create(prompt = prompt) {
            user(event.senderName)
            ImageConfig.push(this)
        }

        return buildMessageChain {
            add(event.message.quote())
            for (item in result.data) {
                val file = store(item = item, folder = folder)
                val image = event.subject.uploadImage(file)

                add(image)
            }
        }
    }

    private suspend fun chat(event: MessageEvent): Message {
        val prompt = event.message.contentToString()
            .removePrefix(MiraiOpenAiConfig.chat)
        launch {
            lock[event.sender.id] = event
            val buffer = StringBuffer(prompt)
            while (isActive) {
                val next = event.nextMessage(ChatConfig.timeout, EventPriority.HIGH, intercept = true)
                val content = next.contentToString()
                if (content == MiraiOpenAiConfig.stop) break
                buffer.append("Human: ").append(content).append('\n')
                buffer.append("AI: ")

                logger.verbose { "prompt: $buffer" }

                val completion = client.completion.create(model = "text-davinci-003") {
                    prompt(buffer.toString())
                    user(event.senderName)
                    ChatConfig.push(this)
                    stop("Human:", "AI:")
                }
                logger.debug { "${completion.model} - ${completion.usage}" }
                val reply = completion.choices.first()
                buffer.append(reply.text).append('\n')
                launch {
                    event.subject.sendMessage(next.quote() + reply.text.removePrefix("\n\n"))
                }
                when (reply.finishReason) {
                    "length" -> logger.warning { "max_tokens not enough for ${next.quote()} " }
                    else -> Unit
                }
            }
        }.invokeOnCompletion { cause ->
            lock.remove(event.sender.id)
            logger.debug { "聊天已终止" }
            if (cause != null) {
                val exception = ExceptionInEventHandlerException(event = event, cause = cause)
                handleException(coroutineContext, exception)
            }
            if (MiraiOpenAiConfig.bye) {
                launch {
                    event.subject.sendMessage(event.message.quote() + "聊天已终止")
                }
            }
        }

        return event.message.quote() + "聊天将开始"
    }

    private suspend fun question(event: MessageEvent): Message {
        val prompt = event.message.contentToString()
            .removePrefix(MiraiOpenAiConfig.question)
        launch {
            lock[event.sender.id] = event
            val buffer = StringBuffer(prompt)
            while (isActive) {
                val next = event.nextMessage(QuestionConfig.timeout, EventPriority.HIGH, intercept = true)
                val content = next.contentToString()
                if (content == MiraiOpenAiConfig.stop) break
                buffer.append("Q: ").append(content).append('\n')
                buffer.append("A: ")

                logger.verbose { "prompt: $buffer" }

                val completion = client.completion.create(model = "text-davinci-003") {
                    prompt(buffer.toString())
                    user(event.senderName)
                    QuestionConfig.push(this)
                    stop("\n")
                }
                logger.debug { "${completion.model} - ${completion.usage}" }
                val reply = completion.choices.first()
                buffer.append(reply.text).append('\n')
                launch {
                    event.subject.sendMessage(next.quote() + reply.text)
                }
                when (reply.finishReason) {
                    "length" -> logger.warning { "max_tokens not enough for ${next.quote()} " }
                    else -> Unit
                }
            }
        }.invokeOnCompletion { cause ->
            lock.remove(event.sender.id)
            logger.debug { "问答已终止" }
            if (cause != null) {
                val exception = ExceptionInEventHandlerException(event = event, cause = cause)
                handleException(coroutineContext, exception)
            }
            if (MiraiOpenAiConfig.bye) {
                launch {
                    event.subject.sendMessage(event.message.quote() + "问答已终止")
                }
            }
        }

        return event.message.quote() + "问答将开始"
    }

    private suspend fun store(item: ImageInfo.Data, folder: File): File {
        return when {
            item.url.isNotEmpty() -> {
                val response = client.http.get(item.url)
                val filename = response.headers[HttpHeaders.ContentDisposition]
                    ?.let { ContentDisposition.parse(it).parameter(ContentDisposition.Parameters.FileName) }
                    ?: "${UUID.randomUUID()}.${response.contentType()?.contentSubtype}"

                val target = folder.resolve(filename)
                response.bodyAsChannel().copyAndClose(target.writeChannel())

                target
            }
            item.base64.isNotEmpty() -> {
                val bytes = Base64.getDecoder().decode(item.base64)

                val target = folder.resolve("${UUID.randomUUID()}.bin")
                target.writeBytes(bytes)

                target
            }
            else -> throw IllegalArgumentException("data is empty")
        }
    }
}