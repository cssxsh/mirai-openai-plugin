package xyz.cssxsh.openai.audio

import kotlinx.serialization.*
import xyz.cssxsh.openai.*
import java.util.*

/**
 * @param model [audio-createTranscription-model](https://platform.openai.com/docs/api-reference/audio/createTranscription#audio-createtranscription-model)
 * @param language [audio-createTranscription-language](https://platform.openai.com/docs/api-reference/audio/createTranscription#audio-createtranscription-language)
 * @param prompt [audio-createTranscription-prompt](https://platform.openai.com/docs/api-reference/audio/createTranscription#audio-createtranscription-prompt)
 * @param format [audio-createTranscription-response_format](https://platform.openai.com/docs/api-reference/audio/createTranscription#audio-createtranscription-response_format)
 * @param temperature [audio-createTranscription-temperature](https://platform.openai.com/docs/api-reference/audio/createTranscription#audio-createtranscription-temperature)
 */
@Serializable
public data class TranscriptionRequest(
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
    public class Builder(@property:OpenAiDsl public var model: String) {

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

        public fun build(): TranscriptionRequest {
            return TranscriptionRequest(
                model = model,
                format = format,
                temperature = temperature,
                language = language
            )
        }
    }
}