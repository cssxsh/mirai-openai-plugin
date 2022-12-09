package xyz.cssxsh.mirai.openai

import kotlinx.coroutines.*
import net.mamoe.mirai.console.data.*
import net.mamoe.mirai.console.plugin.jvm.*
import net.mamoe.mirai.console.util.*
import net.mamoe.mirai.event.*
import net.mamoe.mirai.utils.*
import xyz.cssxsh.mirai.openai.config.*

/**
 * mirai-openai-plugin 插件主类
 */
public object MiraiOpenAiPlugin : KotlinPlugin(
    JvmPluginDescription(
        id = "xyz.cssxsh.mirai.plugin.mirai-openai-plugin",
        name = "mirai-openai-plugin",
        version = "1.0.5",
    ) {
        author("cssxsh")
    }
) {

    private val config: List<PluginConfig> by services()
    private val listeners: List<ListenerHost> by services()

    @Suppress("INVISIBLE_MEMBER")
    private inline fun <reified T : Any> services(): Lazy<List<T>> = lazy {
        with(net.mamoe.mirai.console.internal.util.PluginServiceHelper) {
            jvmPluginClasspath.pluginClassLoader
                .findServices<T>()
                .loadAllServices()
        }
    }

    override fun onEnable() {
        for (config in config) config.reload()

        if (MiraiOpenAiConfig.token.isEmpty()) {
            val token = runBlocking { ConsoleInput.requestInput(hint = "请输入 OPENAI_TOKEN") }
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
        for (config in config) config.save()

        for (listener in listeners) (listener as SimpleListenerHost).registerTo(globalEventChannel())

        if (MiraiOpenAiConfig.permission) {
            logger.info { "权限检查已开启" }
            MiraiOpenAiListener.completion
            MiraiOpenAiListener.image
            MiraiOpenAiListener.chat
            MiraiOpenAiListener.question
        } else {
            logger.info { "权限检查未开启" }
        }
    }

    override fun onDisable() {
        for (listener in listeners) (listener as SimpleListenerHost).cancel()
    }
}