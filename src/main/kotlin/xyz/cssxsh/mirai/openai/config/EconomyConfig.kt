package xyz.cssxsh.mirai.openai.config

import net.mamoe.mirai.console.data.*

public object EconomyConfig : ReadOnlyPluginConfig("economy") {

    @ValueName("sign_plus_assign")
    public val sign: Int by value(1024)
}