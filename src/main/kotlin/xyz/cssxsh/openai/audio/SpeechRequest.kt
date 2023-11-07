package xyz.cssxsh.openai.audio

import kotlinx.serialization.*
import xyz.cssxsh.openai.*
import java.util.*

/**
 * @param model [audio-createSpeech-model](https://platform.openai.com/docs/api-reference/audio/createSpeech#audio-createspeech-model)
 * @param input [audio-createSpeech-input](https://platform.openai.com/docs/api-reference/audio/createSpeech#audio-createspeech-input)
 * @param voice [audio-createSpeech-voice](https://platform.openai.com/docs/api-reference/audio/createSpeech#audio-createspeech-voice)
 * @param format [audio-createSpeech-response_format](https://platform.openai.com/docs/api-reference/audio/createSpeech#audio-createspeech-response_format)
 * @param speed [audio-createSpeech-speed](https://platform.openai.com/docs/api-reference/audio/createSpeech#audio-createspeech-speed)
 */
@Serializable
public data class SpeechRequest(
    @SerialName("model")
    val model: String,
    @SerialName("input")
    val input: String,
    @SerialName("voice")
    val voice: String,
    @SerialName("response_format")
    val format: String = "json",
    @SerialName("speed")
    val speed: Double = 1.0
)