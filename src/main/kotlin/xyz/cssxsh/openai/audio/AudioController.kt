package xyz.cssxsh.openai.audio

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import xyz.cssxsh.openai.*

/**
 * [Audio](https://platform.openai.com/docs/api-reference/audio)
 */
public class AudioController(private val client: OpenAiClient) {

    /**
     * [Create speech](https://platform.openai.com/docs/api-reference/audio/createSpeech)
     */
    public suspend fun speech(request: SpeechRequest): ByteArray {
        val response = client.http.post("https://api.openai.com/v1/audio/speech") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }

        return response.body()
    }

    /**
     * [Create transcription](https://platform.openai.com/docs/api-reference/audio/createTranscription)
     */
    public suspend fun transcription(file: ByteArray, request: TranscriptionRequest): AudioInfo {
        val response = client.http.submitFormWithBinaryData("https://api.openai.com/v1/audio/transcriptions", formData {
            append("file", file)
            append("model", request.model)
            append("language", request.language)
            append("prompt", request.prompt)
            append("response_format", request.format)
            append("temperature", request.temperature)
        })

        return response.body()
    }

    /**
     * [Create transcription](https://platform.openai.com/docs/api-reference/audio/createTranscription)
     */
    public suspend fun transcription(
        file: ByteArray,
        model: String,
        block: TranscriptionRequest.Builder.() -> Unit
    ): AudioInfo {
        return transcription(file = file, request = TranscriptionRequest.Builder(model = model).apply(block).build())
    }

    /**
     * [Create translation](https://platform.openai.com/docs/api-reference/audio/createTranslation)
     */
    public suspend fun translation(file: ByteArray, request: TranscriptionRequest): AudioInfo {
        val response = client.http.submitFormWithBinaryData("https://api.openai.com/v1/audio/translations", formData {
            append("file", file)
            append("model", request.model)
            append("prompt", request.prompt)
            append("response_format", request.format)
            append("temperature", request.temperature)
        })

        return response.body()
    }

    /**
     * [Create transcription](https://platform.openai.com/docs/api-reference/audio/createTranslation)
     */
    public suspend fun translation(
        file: ByteArray,
        model: String,
        block: TranscriptionRequest.Builder.() -> Unit
    ): AudioInfo {
        return translation(file = file, request = TranscriptionRequest.Builder(model = model).apply(block).build())
    }
}