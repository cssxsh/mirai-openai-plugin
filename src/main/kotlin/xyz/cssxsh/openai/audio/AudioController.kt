package xyz.cssxsh.openai.audio

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import xyz.cssxsh.openai.*

/**
 * [Audio](https://platform.openai.com/docs/api-reference/audio)
 */
public class AudioController(private val client: OpenAiClient) {

    /**
     * [Create transcription](https://platform.openai.com/docs/api-reference/audio/create)
     */
    public suspend fun transcription(request: AudioRequest): AudioInfo {
        val response = client.http.post("https://api.openai.com/v1/completions") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }

        return response.body()
    }

    /**
     * [Create transcription](https://platform.openai.com/docs/api-reference/audio/create)
     */
    public suspend fun transcription(file: String, model: String, block: AudioRequest.Builder.() -> Unit): AudioInfo {
        return transcription(request = AudioRequest.Builder(file = file, model = model).apply(block).build())
    }

    /**
     * [Create translation](https://platform.openai.com/docs/api-reference/audio/create)
     */
    public suspend fun translation(request: AudioRequest): AudioInfo {
        val response = client.http.post("https://api.openai.com/v1/completions") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }

        return response.body()
    }

    /**
     * [Create transcription](https://platform.openai.com/docs/api-reference/audio/create)
     */
    public suspend fun translation(file: String, model: String, block: AudioRequest.Builder.() -> Unit): AudioInfo {
        return translation(request = AudioRequest.Builder(file = file, model = model).apply(block).build())
    }
}