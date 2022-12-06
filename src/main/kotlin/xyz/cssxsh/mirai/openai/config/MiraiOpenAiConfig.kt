package xyz.cssxsh.mirai.openai.config

import kotlinx.serialization.*
import net.mamoe.mirai.console.data.*
import xyz.cssxsh.openai.*

@PublishedApi
internal object MiraiOpenAiConfig : ReadOnlyPluginConfig("openai"), OpenAiClientConfig {
    @ValueName("proxy")
    override val proxy: String by value("")

    @ValueName("doh")
    override val doh: String by value("https://public.dns.iij.jp/dns-query")

    @ValueName("ipv6")
    override val ipv6: Boolean by value(true)

    @ValueName("timeout")
    override val timeout: Long by value(30_000L)

    @ValueName("token")
    override val token: String by value(System.getenv("OPENAI_TOKEN").orEmpty())

    @ValueName("error_reply")
    val reply: Boolean by value(true)

    @ValueName("completion_prefix")
    val completion: String by value("> ")

    @ValueName("image_prefix")
    val image: String by value("? ")

    @ValueName("chat_prefix")
    val chat: String by value("chat")

    @ValueName("image_folder")
    val folder: String by value("run")
}