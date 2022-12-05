package xyz.cssxsh.openai.completion

import kotlinx.serialization.*

/**
 * @param model [create-model](https://beta.openai.com/docs/api-reference/completions/create#completions/create-model)
 * @param prompt [create-prompt](https://beta.openai.com/docs/api-reference/completions/create#completions/create-prompt)
 * @param suffix [create-suffix](https://beta.openai.com/docs/api-reference/completions/create#completions/create-suffix)
 * @param maxTokens [create-max_tokens](https://beta.openai.com/docs/api-reference/completions/create#completions/create-max_tokens)
 * @param temperature [create-temperature](https://beta.openai.com/docs/api-reference/completions/create#completions/create-temperature)
 * @param topP [create-top_p](https://beta.openai.com/docs/api-reference/completions/create#completions/create-top_p)
 * @param n [create-n](https://beta.openai.com/docs/api-reference/completions/create#completions/create-n)
 * @param stream [create-stream](https://beta.openai.com/docs/api-reference/completions/create#completions/create-stream)
 * @param logprobs [create-logprobs](https://beta.openai.com/docs/api-reference/completions/create#completions/create-logprobs)
 * @param echo [create-echo](https://beta.openai.com/docs/api-reference/completions/create#completions/create-echo)
 * @param stop [create-stop](https://beta.openai.com/docs/api-reference/completions/create#completions/create-stop)
 * @param presencePenalty [create-presence_penalty](https://beta.openai.com/docs/api-reference/completions/create#completions/create-presence_penalty)
 * @param frequencyPenalty [create-frequency_penalty](https://beta.openai.com/docs/api-reference/completions/create#completions/create-frequency_penalty)
 * @param bestOf [create-best_of](https://beta.openai.com/docs/api-reference/completions/create#completions/create-best_of)
 * @param logitBias [create-logit_bias](https://beta.openai.com/docs/api-reference/completions/create#completions/create-logit_bias)
 * @param user [create-user](https://beta.openai.com/docs/api-reference/completions/create#completions/create-user)
 */
@Serializable
public data class CompletionRequest(
    @SerialName("model")
    val model: String,
    @SerialName("prompt")
    val prompt: String = "", // XXX: array
    @SerialName("suffix")
    val suffix: String? = null,
    @SerialName("max_tokens")
    val maxTokens: Int = 16,
    @SerialName("temperature")
    val temperature: Double = 1.0,
    @SerialName("top_p")
    val topP: Double = 1.0,
    @SerialName("n")
    val n: Int = 1,
    @SerialName("stream")
    val stream: Boolean = false,
    @SerialName("logprobs")
    val logprobs: Int? = null,
    @SerialName("echo")
    val echo: Boolean = false,
    @SerialName("stop")
    val stop: String? = null,// XXX: array
    @SerialName("presence_penalty")
    val presencePenalty: Double = 0.0,
    @SerialName("frequency_penalty")
    val frequencyPenalty: Double = 0.0,
    @SerialName("best_of")
    val bestOf: Int = 1,
    @SerialName("logit_bias")
    val logitBias: Map<String, Int>? = null,
    @SerialName("user")
    val user: String = ""
)