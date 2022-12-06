package xyz.cssxsh.openai.completion

import kotlinx.serialization.*
import xyz.cssxsh.openai.*

/**
 * @param model [create-model](https://beta.openai.com/docs/api-reference/completions/create#completions/create-model)
 * @param prompt [create-prompt](https://beta.openai.com/docs/api-reference/completions/create#completions/create-prompt)
 * @param suffix [create-suffix](https://beta.openai.com/docs/api-reference/completions/create#completions/create-suffix)
 * @param maxTokens [create-max_tokens](https://beta.openai.com/docs/api-reference/completions/create#completions/create-max_tokens)
 * @param temperature [create-temperature](https://beta.openai.com/docs/api-reference/completions/create#completions/create-temperature)
 * @param topP [create-top_p](https://beta.openai.com/docs/api-reference/completions/create#completions/create-top_p)
 * @param number [create-n](https://beta.openai.com/docs/api-reference/completions/create#completions/create-n)
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
    val prompt: List<String> = emptyList(),
    @SerialName("suffix")
    val suffix: String? = null,
    @SerialName("max_tokens")
    val maxTokens: Int = 16,
    @SerialName("temperature")
    val temperature: Double = 1.0,
    @SerialName("top_p")
    val topP: Double = 1.0,
    @SerialName("n")
    val number: Int = 1,
    @SerialName("stream")
    val stream: Boolean = false,
    @SerialName("logprobs")
    val logprobs: Int? = null,
    @SerialName("echo")
    val echo: Boolean = false,
    @SerialName("stop")
    val stop: List<String>? = null,
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
) {
    public class Builder(@property:OpenAiDsl public var model: String) {
        @OpenAiDsl
        public fun model(value: String): Builder = apply {
            model = value
        }

        @OpenAiDsl
        public var prompt: List<String> = emptyList()
        @OpenAiDsl
        public fun prompt(values: List<String>): Builder = apply {
            prompt = values
        }
        @OpenAiDsl
        public fun prompt(vararg values: String): Builder = apply {
            prompt = values.asList()
        }

        @OpenAiDsl
        private var suffix: String? = null
        @OpenAiDsl
        public fun suffix(value: String): Builder = apply {
            suffix = value
        }

        @OpenAiDsl
        public var maxTokens: Int = 16
        @OpenAiDsl
        public fun maxTokens(value: Int): Builder = apply {
            maxTokens = value
        }

        @OpenAiDsl
        public var temperature: Double = 1.0
        @OpenAiDsl
        public fun temperature(value: Double): Builder = apply {
            temperature = value
        }

        @OpenAiDsl
        public var topP: Double = 1.0
        @OpenAiDsl
        public fun topP(value: Double): Builder = apply {
            topP = value
        }

        @OpenAiDsl
        public var number: Int = 1
        @OpenAiDsl
        public fun number(value: Int): Builder = apply {
            number = value
        }

        @OpenAiDsl
        public var stream: Boolean = false
        @OpenAiDsl
        public fun stream(value: Boolean): Builder = apply {
            stream = value
        }

        @OpenAiDsl
        public var logprobs: Int? = null
        @OpenAiDsl
        public fun logprobs(value: Int): Builder = apply {
            logprobs = value
        }

        @OpenAiDsl
        public var echo: Boolean = false
        @OpenAiDsl
        public fun echo(value: Boolean): Builder = apply {
            echo = value
        }

        @OpenAiDsl
        public var stop: List<String>? = null
        @OpenAiDsl
        public fun stop(values: List<String>): Builder = apply {
            stop = values
        }
        @OpenAiDsl
        public fun stop(vararg values: String): Builder = apply {
            stop = values.asList()
        }

        @OpenAiDsl
        public var presencePenalty: Double = 0.0
        @OpenAiDsl
        public fun presencePenalty(value: Double): Builder = apply {
            presencePenalty = value
        }

        @OpenAiDsl
        public var frequencyPenalty: Double = 0.0
        @OpenAiDsl
        public fun frequencyPenalty(value: Double): Builder = apply {
            frequencyPenalty = value
        }

        @OpenAiDsl
        public var bestOf: Int = 1
        @OpenAiDsl
        public fun bestOf(value: Int): Builder = apply {
            bestOf = value
        }

        @OpenAiDsl
        public var logitBias: Map<String, Int>? = null
        @OpenAiDsl
        public fun logitBias(value: Map<String, Int>): Builder = apply {
            logitBias = value
        }
        @OpenAiDsl
        public fun logitBias(vararg pairs: Pair<String, Int>): Builder = apply {
            logitBias = mapOf(pairs = pairs)
        }

        @OpenAiDsl
        public var user: String = ""
        @OpenAiDsl
        public fun user(value: String): Builder = apply {
            user = value
        }

        public fun build(): CompletionRequest {
            return CompletionRequest(
                model = model,
                prompt = prompt,
                suffix = suffix,
                maxTokens = maxTokens,
                temperature = temperature,
                topP = topP,
                number = number,
                stream = stream,
                logprobs = logprobs,
                echo = echo,
                stop = stop,
                presencePenalty = presencePenalty,
                frequencyPenalty = frequencyPenalty,
                bestOf = bestOf,
                logitBias = logitBias,
                user = user
            )
        }
    }
}