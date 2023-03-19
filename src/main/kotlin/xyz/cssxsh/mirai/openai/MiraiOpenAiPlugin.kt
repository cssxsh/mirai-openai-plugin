package xyz.cssxsh.mirai.openai

import kotlinx.coroutines.*
import net.mamoe.mirai.console.*
import net.mamoe.mirai.console.data.*
import net.mamoe.mirai.console.plugin.*
import net.mamoe.mirai.console.plugin.jvm.*
import net.mamoe.mirai.console.util.*
import net.mamoe.mirai.event.*
import net.mamoe.mirai.utils.*
import xyz.cssxsh.mirai.openai.config.*
import xyz.cssxsh.mirai.openai.data.*

/**
 * mirai-openai-plugin 插件主类
 */
public object MiraiOpenAiPlugin : KotlinPlugin(
    JvmPluginDescription(
        id = "xyz.cssxsh.mirai.plugin.mirai-openai-plugin",
        name = "mirai-openai-plugin",
        version = "1.3.3",
    ) {
        author("cssxsh")

        dependsOn("xyz.cssxsh.mirai.plugin.mirai-economy-core", true)
    }
) {

    @PublishedApi
    internal val config: List<PluginConfig> by services()

    @PublishedApi
    internal val data: List<PluginData> by services()

    @PublishedApi
    internal val listeners: List<ListenerHost> by services()

    override fun onEnable() {
        for (config in config) config.reload()
        for (config in data) config.reload()

        if (MiraiOpenAiConfig.token.isEmpty()) {
            val token: String = runBlocking {
                var temp = ""
                while (isActive) {
                    temp = ConsoleInput.requestInput(hint = "请输入 OpenAI Secret Key")
                    if ("*" in temp) {
                        logger.warning { "你输入 OpenAI Secret Key 有误，请重新输入" }
                        continue
                    }
                    break
                }
                temp
            }

            @OptIn(ConsoleExperimentalApi::class)
            @Suppress("UNCHECKED_CAST")
            val value = MiraiOpenAiConfig.findBackingFieldValue<String>("token") as Value<String>
            value.value = token
        }
        if (MiraiOpenAiConfig.folder == "run") {
            @OptIn(ConsoleExperimentalApi::class)
            @Suppress("UNCHECKED_CAST")
            val value = MiraiOpenAiConfig.findBackingFieldValue<String>("image_folder") as Value<String>
            val folder = resolveDataFile("image")
            folder.mkdirs()
            value.value = folder.absolutePath
        }
        if (MiraiOpenAiConfig.proxy.isNotEmpty()) {
            logger.info { "代理已配置: ${MiraiOpenAiConfig.proxy}" }
        }

        for (config in config) config.save()

        for (listener in listeners) (listener as SimpleListenerHost).registerTo(globalEventChannel())

        if (MiraiOpenAiTokensData.economy) {
            logger.info { "经济系统已接入" }
        }

        if (MiraiOpenAiConfig.permission) {
            logger.info { "权限检查已开启" }
            MiraiOpenAiListener.completion
            MiraiOpenAiListener.image
            MiraiOpenAiListener.chat
            MiraiOpenAiListener.question
            MiraiOpenAiListener.reload
            MiraiOpenAiListener.economy
        } else {
            logger.info { "权限检查未开启" }
        }

        if (MiraiOpenAiConfig.chatByAt) {
            logger.warning { "@Bot 触发聊天已开启, 手机端引用消息会自带@，请注意不要误触" }
        }
    }

    override fun onDisable() {
        for (listener in listeners) (listener as SimpleListenerHost).cancel()
    }
}