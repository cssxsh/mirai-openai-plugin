package xyz.cssxsh.mirai.openai

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.cio.*
import io.ktor.utils.io.*
import net.mamoe.mirai.contact.Contact.Companion.uploadImage
import net.mamoe.mirai.event.*
import net.mamoe.mirai.event.events.*
import net.mamoe.mirai.message.data.*
import xyz.cssxsh.mirai.openai.config.*
import xyz.cssxsh.openai.*
import xyz.cssxsh.openai.image.*
import java.io.File
import java.util.Base64
import java.util.UUID

@PublishedApi
internal object MiraiOpenAiListener : SimpleListenerHost() {
    private val client = OpenAiClient(config = MiraiOpenAiConfig)
    private val folder = File(MiraiOpenAiConfig.folder)

    @EventHandler
    suspend fun MessageEvent.handle() {
        val content = message.contentToString()
        val message: Message = when {
            content.startsWith(MiraiOpenAiConfig.completion) -> completion(event = this)
            content.startsWith(MiraiOpenAiConfig.image) -> image(event = this)
            else -> return
        }

        subject.sendMessage(message)
    }

    private suspend fun completion(event: MessageEvent): Message {
        val prompt = event.message.contentToString()
            .removePrefix(MiraiOpenAiConfig.completion)
        val completion = client.completion.create(model = "text-davinci-003") {
            prompt(prompt)
            user(event.senderName)
            maxTokens(256)
        }

        return buildMessageChain {
            for (choice in completion.choices) {
                appendLine(choice.text)
            }
        }
    }

    private suspend fun image(event: MessageEvent): Message {
        val prompt = event.message.contentToString()
            .removePrefix(MiraiOpenAiConfig.image)
        val result = client.image.create(prompt = prompt) {
            // number(3)
        }

        return buildMessageChain {
            for (item in result.data) {
                val file = store(item = item, folder = folder)
                val image = event.subject.uploadImage(file)

                add(image)
            }
        }
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