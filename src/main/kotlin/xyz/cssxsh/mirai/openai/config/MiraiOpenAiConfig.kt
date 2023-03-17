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
    @ValueDescription("OPENAI_TOKEN https://platform.openai.com/account/api-keys")
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

    @ValueName("chat_by_at")
    @ValueDescription("聊天模型触发于@")
    val chatByAt: Boolean by value(false)

    @ValueName("question_prefix")
    @ValueDescription("问答模型触发前缀")
    val question: String by value("Q&A")

    @ValueName("reload_prefix")
    @ValueDescription("重载配置触发前缀")
    val reload: String by value("openai-reload")

    @ValueName("economy_set_prefix")
    @ValueDescription("经济设置触发前缀")
    val tokens: String by value("tokens")

    @ValueName("bind_set_prefix")
    @ValueDescription("绑定设置触发前缀")
    val bind: String by value("bind")

    @ValueName("stop")
    @ValueDescription("停止聊天或问答")
    val stop: String by value("stop")

    @ValueName("keep_prefix_check")
    @ValueDescription("保持前缀检查")
    val prefix: Boolean by value(false)

    @ValueName("at_once")
    @ValueDescription("立刻开始聊天/问答")
    val atOnce: Boolean by value(false)

    @ValueName("image_folder")
    @ValueDescription("图片生成缓存文件夹")
    val folder: String by value("run")

    @ValueName("chat_limit")
    @ValueDescription("聊天服务个数限制")
    val limit: Int by value(10)

    @ValueName("has_permission")
    @ValueDescription("权限检查")
    val permission: Boolean by value(false)

    @ValueName("has_economy")
    @ValueDescription("接入经济系统")
    val economy: Boolean by value(true)
}