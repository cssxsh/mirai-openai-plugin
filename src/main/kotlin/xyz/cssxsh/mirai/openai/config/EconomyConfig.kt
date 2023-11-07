package xyz.cssxsh.mirai.openai.config

import net.mamoe.mirai.console.data.*

public object EconomyConfig : ReadOnlyPluginConfig(saveName = "economy") {

    @ValueName("sign_plus_assign")
    @ValueDescription("签到增加的tokens数量")
    public val sign: Int by value(1024)
}