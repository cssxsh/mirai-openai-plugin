package xyz.cssxsh.mirai.openai.config

import net.mamoe.mirai.console.data.*
import xyz.cssxsh.openai.chat.*
import xyz.cssxsh.openai.completion.*

@PublishedApi
internal object ChatConfig : ReadOnlyPluginConfig("chat") {

    @ValueName("timeout")
    @ValueDescription("等待停止时间")
    val timeout: Long by value(60_000L)

    @ValueName("gpt_model")
    @ValueDescription("GPT Model")
    val model: String by value("gpt-3.5-turbo-0301")

    @ValueName("max_tokens")
    @ValueDescription("Maximum length")
    val maxTokens: Int by value(512)

    @ValueName("temperature")
    @ValueDescription("Temperature")
    val temperature: Double by value(0.9)

    @ValueName("top_p")
    @ValueDescription("Top P")
    val topP: Double by value(1.0)

    @ValueName("presence_penalty")
    @ValueDescription("Presence Penalty")
    val presencePenalty: Double by value(0.6)

    @ValueName("frequency_penalty")
    @ValueDescription("Frequency Penalty")
    val frequencyPenalty: Double by value(0.0)

    fun push(builder: ChatRequest.Builder) {
        builder.model = model
        builder.maxTokens = maxTokens
        builder.temperature = temperature
        builder.topP = topP
        builder.presencePenalty = presencePenalty
        builder.frequencyPenalty = frequencyPenalty
    }
}