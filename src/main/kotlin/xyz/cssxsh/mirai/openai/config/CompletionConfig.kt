package xyz.cssxsh.mirai.openai.config

import net.mamoe.mirai.console.data.*
import xyz.cssxsh.openai.completion.*

@PublishedApi
internal object CompletionConfig : ReadOnlyPluginConfig("completion") {

    @ValueName("model")
    val model: String by value("text-davinci-003")

    @ValueName("suffix")
    val suffix: String by value()

    @ValueName("max_tokens")
    val maxTokens: Int by value(256)

    @ValueName("temperature")
    val temperature: Double by value(1.0)

    @ValueName("top_p")
    val topP: Double by value(1.0)

    @ValueName("n")
    val number: Int by value(1)

    @ValueName("echo")
    val echo: Boolean by value(false)

    @ValueName("stop")
    val stop: List<String> by value()

    @ValueName("presence_penalty")
    val presencePenalty: Double by value(0.0)

    @ValueName("frequency_penalty")
    val frequencyPenalty: Double by value(0.0)

    @ValueName("best_of")
    val bestOf: Int by value(1)

    @ValueName("logit_bias")
    val logitBias: Map<String, Int> by value()

    fun push(builder: CompletionRequest.Builder) {
        builder.model = model
        if (suffix.isNotEmpty()) builder.suffix = suffix
        builder.maxTokens = maxTokens
        builder.temperature = temperature
        builder.topP = topP
        builder.number = number
        builder.echo = echo
        if (stop.isNotEmpty()) builder.stop = stop
        builder.presencePenalty = presencePenalty
        builder.frequencyPenalty = frequencyPenalty
        builder.bestOf = bestOf
        if (logitBias.isNotEmpty()) builder.logitBias = logitBias
    }
}