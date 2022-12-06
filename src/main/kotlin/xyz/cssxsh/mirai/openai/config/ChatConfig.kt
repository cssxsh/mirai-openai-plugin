package xyz.cssxsh.mirai.openai.config

import net.mamoe.mirai.console.data.*
import xyz.cssxsh.openai.completion.*

@PublishedApi
internal object ChatConfig : ReadOnlyPluginConfig("chat") {

    @ValueName("model")
    @ValueDescription("Model")
    val model: String by value("text-davinci-003")

    @ValueName("max_tokens")
    @ValueDescription("Maximum length")
    val maxTokens: Int by value(256)

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

    fun push(builder: CompletionRequest.Builder) {
        builder.model = model
        builder.maxTokens = maxTokens
        builder.temperature = temperature
        builder.topP = topP
        builder.presencePenalty = presencePenalty
        builder.frequencyPenalty = frequencyPenalty
    }
}