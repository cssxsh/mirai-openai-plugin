package xyz.cssxsh.openai

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.compression.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.statement.*
import io.ktor.serialization.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.utils.io.charsets.*
import kotlinx.serialization.json.*
import xyz.cssxsh.openai.completion.*
import xyz.cssxsh.openai.edit.*
import xyz.cssxsh.openai.embedding.*
import xyz.cssxsh.openai.file.*
import xyz.cssxsh.openai.finetune.*
import xyz.cssxsh.openai.image.*
import xyz.cssxsh.openai.model.*
import xyz.cssxsh.openai.moderation.*

public open class OpenAiClient(internal val config: OpenAiClientConfig) {
    public open val http: HttpClient = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(json = Json)
        }
        install(HttpTimeout) {
            socketTimeoutMillis = config.timeout
            connectTimeoutMillis = config.timeout
            requestTimeoutMillis = null
        }
        Auth {
            bearer {
                loadTokens {
                    BearerTokens(config.token, "")
                }
                refreshTokens {
                    BearerTokens(config.token, "")
                }
                sendWithoutRequest { builder ->
                    builder.url.host == "api.openai.com"
                }
            }
        }
        HttpResponseValidator {
            validateResponse { response ->
                val statusCode = response.status.value
                val originCall = response.call
                if (statusCode < 400) return@validateResponse

                val exceptionCall = originCall.save()
                val exceptionResponse = exceptionCall.response

                throw try {
                    val error = exceptionResponse.body<ErrorInfoWrapper>().error
                    OpenAiException(info = error)
                } catch (_: ContentConvertException) {
                    val exceptionResponseText = try {
                        exceptionResponse.bodyAsText()
                    } catch (_: MalformedInputException) {
                        "<body failed decoding>"
                    }
                    when (statusCode) {
                        in 400..499 -> {
                            ClientRequestException(response, exceptionResponseText)
                        }
                        in 500..599 -> {
                            ServerResponseException(response, exceptionResponseText)
                        }
                        else -> ResponseException(response, exceptionResponseText)
                    }
                }
            }
        }
        BrowserUserAgent()
        ContentEncoding()
        engine {
            config {
                apply(config = config)
            }
        }
    }
    public open val model: ModelController by lazy { ModelController(this) }
    public open val completion: CompletionController by lazy { CompletionController(this) }
    public open val edit: EditController by lazy { EditController(this) }
    public open val image: ImageController by lazy { ImageController(this) }
    public open val embedding: EmbeddingController by lazy { EmbeddingController(this) }
    public open val file: FileController by lazy { FileController(this) }
    public open val finetune: FineTuneController by lazy { FineTuneController(this) }
    public open val moderation: ModerationController by lazy { ModerationController(this) }
}