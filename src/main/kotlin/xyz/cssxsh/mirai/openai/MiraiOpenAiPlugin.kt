package xyz.cssxsh.mirai.openai

import net.mamoe.mirai.console.plugin.jvm.*

/**
 * mirai-openai-plugin 插件主类
 */
public object MiraiOpenAiPlugin: KotlinPlugin(
    JvmPluginDescription(
        id = "xyz.cssxsh.mirai.plugin.mirai-openai-plugin",
        name = "mirai-openai-plugin",
        version = "1.0.0",
    ) {
        author("cssxsh")
    }
) {
    override fun onEnable() {
        //
    }

    override fun onDisable() {
        //
    }
}