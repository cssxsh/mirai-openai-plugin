package xyz.cssxsh.openai.audio

import kotlinx.serialization.*
import xyz.cssxsh.openai.*
import java.util.*

/**
 * @param file [create-file](https://platform.openai.com/docs/api-reference/audio/create#audio/create-file)
 * @param model [create-model](https://platform.openai.com/docs/api-reference/audio/create#audio/create-model)
 * @param prompt [create-prompt](https://platform.openai.com/docs/api-reference/audio/create#audio/create-prompt)
 * @param format [create-response_format](https://platform.openai.com/docs/api-reference/audio/create#audio/create-response_format)
 * @param temperature [create-temperature](https://platform.openai.com/docs/api-reference/audio/create#audio/create-temperature)
 */
@Serializable
public data class AudioRequest(
    @SerialName("file")
    val file: String,
    @SerialName("model")
    val model: String,
    @SerialName("prompt")
    val prompt: String = "",
    @SerialName("response_format")
    val format: String = "json",
    @SerialName("temperature")
    val temperature: Double = 0.0,
    @SerialName("language")
    val language: String = Locale.getDefault().language
) {
    public class Builder(@property:OpenAiDsl public var file: String, @property:OpenAiDsl public var model: String) {
        @OpenAiDsl
        public fun file(value: String): Builder = apply {
            file = value
        }

        @OpenAiDsl
        public fun model(value: String): Builder = apply {
            model = value
        }

        @OpenAiDsl
        public var format: String = "json"

        @OpenAiDsl
        public fun format(value: String): Builder = apply {
            format = value
        }

        @OpenAiDsl
        public var temperature: Double = 1.0

        @OpenAiDsl
        public fun temperature(value: Double): Builder = apply {
            temperature = value
        }

        @OpenAiDsl
        public var language: String = Locale.getDefault().language

        @OpenAiDsl
        public fun language(value: String): Builder = apply {
            language = value
        }

        @OpenAiDsl
        public fun language(locale: Locale): Builder = apply {
            language = locale.language
        }

        public fun build(): AudioRequest {
            return AudioRequest(
                file = file,
                model = model,
                format = format,
                temperature = temperature,
                language = language
            )
        }
    }
}