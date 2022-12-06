package xyz.cssxsh.mirai.openai

import kotlinx.coroutines.*
import net.mamoe.mirai.console.data.*
import net.mamoe.mirai.console.plugin.jvm.*
import net.mamoe.mirai.console.util.*
import net.mamoe.mirai.event.*
import xyz.cssxsh.mirai.openai.config.*

/**
 * mirai-openai-plugin 插件主类
 */
public object MiraiOpenAiPlugin : KotlinPlugin(
    JvmPluginDescription(
        id = "xyz.cssxsh.mirai.plugin.mirai-openai-plugin",
        name = "mirai-openai-plugin",
        version = "1.0.0",
    ) {
        author("cssxsh")
    }
) {
    override fun onEnable() {
        MiraiOpenAiConfig.reload()
        CompletionConfig.reload()

        if (MiraiOpenAiConfig.token.isEmpty()) {
            val token = runBlocking { ConsoleInput.requestInput(hint = "请输入 OPENAI_TOKEN") }
            @OptIn(ConsoleExperimentalApi::class)
            @Suppress("UNCHECKED_CAST")
            val value = MiraiOpenAiConfig.findBackingFieldValue<String>("token") as Value<String>
            value.value = token
            MiraiOpenAiConfig.save()
        }

        if (MiraiOpenAiConfig.folder == "run") {
            @OptIn(ConsoleExperimentalApi::class)
            @Suppress("UNCHECKED_CAST")
            val value = MiraiOpenAiConfig.findBackingFieldValue<String>("image_folder") as Value<String>
            val folder = resolveDataFile("image")
            folder.mkdirs()
            value.value = folder.absolutePath
            MiraiOpenAiConfig.save()
        }

        MiraiOpenAiListener.registerTo(globalEventChannel())
    }

    override fun onDisable() {
        MiraiOpenAiListener.cancel()
    }
}