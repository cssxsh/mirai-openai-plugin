package xyz.cssxsh.mirai.openai.config

import kotlinx.serialization.*
import net.mamoe.mirai.console.data.*
import xyz.cssxsh.openai.*

@PublishedApi
internal object MiraiOpenAiConfig : ReadOnlyPluginConfig("openai"), OpenAiClientConfig {

    @ValueName("proxy")
    override val proxy: String by value("")

    @ValueName("doh")
    @ValueDescription("DNS Over Https")
    override val doh: String by value("https://public.dns.iij.jp/dns-query")

    @ValueName("ipv6")
    @ValueDescription("启用 ipv6 连接，如果本机因不支持ipv6导致异常，可以尝试关闭")
    override val ipv6: Boolean by value(true)

    @ValueName("timeout")
    @ValueDescription("API 超时时间, 如果出现 TimeoutException 时, 请尝试调大")
    override val timeout: Long by value(30_000L)

    @ValueName("token")
    @ValueDescription("OPENAI_TOKEN https://beta.openai.com/account/api-keys")
    override val token: String by value(System.getenv("OPENAI_TOKEN").orEmpty())

    @ValueName("error_reply")
    @ValueDescription("发生错误时回复用户")
    val reply: Boolean by value(true)

    @ValueName("end_reply")
    @ValueDescription("停止聊天时回复用户")
    val bye: Boolean by value(false)

    @ValueName("completion_prefix")
    @ValueDescription("自定义模型触发前缀")
    val completion: String by value("> ")

    @ValueName("image_prefix")
    @ValueDescription("图片生成触发前缀")
    val image: String by value("? ")

    @ValueName("chat_prefix")
    @ValueDescription("聊天模型触发前缀")
    val chat: String by value("chat")

    @ValueName("question_prefix")
    @ValueDescription("问答模型触发前缀")
    val question: String by value("Q&A")

    @ValueName("stop")
    @ValueDescription("停止聊天或问答")
    val stop: String by value("stop")

    @ValueName("image_folder")
    @ValueDescription("图片生成缓存文件夹")
    val folder: String by value("run")
}