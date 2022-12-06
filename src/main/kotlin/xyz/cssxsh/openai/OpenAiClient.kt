package xyz.cssxsh.openai

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.compression.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.*
import okhttp3.Dns
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.dnsoverhttps.DnsOverHttps
import xyz.cssxsh.openai.completion.*
import xyz.cssxsh.openai.edit.*
import xyz.cssxsh.openai.embedding.*
import xyz.cssxsh.openai.file.*
import xyz.cssxsh.openai.finetune.*
import xyz.cssxsh.openai.image.*
import xyz.cssxsh.openai.model.*
import xyz.cssxsh.openai.moderation.*
import java.net.*

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
        BrowserUserAgent()
        ContentEncoding()
        engine {
            config {
                dns(object : Dns {
                    private val doh = DnsOverHttps.Builder()
                        .client(okhttp3.OkHttpClient())
                        .url(config.doh.toHttpUrl())
                        .includeIPv6(config.ipv6)
                        .build()

                    override fun lookup(hostname: String): List<InetAddress> {
                        return try {
                            doh.lookup(hostname)
                        } catch (_: UnknownHostException) {
                            Dns.SYSTEM.lookup(hostname)
                        }
                    }
                })
                config.proxy.toHttpUrlOrNull()?.let {
                    val proxy = when (it.scheme) {
                        "socks" -> Proxy(Proxy.Type.SOCKS, InetSocketAddress(it.host, it.port))
                        "http" -> Proxy(Proxy.Type.HTTP, InetSocketAddress(it.host, it.port))
                        else -> Proxy.NO_PROXY
                    }
                    proxy(proxy)
                }
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