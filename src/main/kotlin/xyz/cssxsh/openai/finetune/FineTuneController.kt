package xyz.cssxsh.openai.finetune

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import xyz.cssxsh.openai.*

/**
 * [Fine-tunes](https://platform.openai.com/docs/api-reference/fine-tunes)
 */
@Deprecated("API Deprecated")
public class FineTuneController(private val client: OpenAiClient) {

    /**
     * [Create fine-tunes](https://platform.openai.com/docs/api-reference/fine-tunes/create)
     */
    public suspend fun create(request: FineTuneRequest): FineTuneInfo {
        val response = client.http.post("https://api.openai.com/v1/fine-tunes") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }

        return response.body()
    }

    /**
     * [List fine-tunes](https://platform.openai.com/docs/api-reference/fine-tunes/list)
     */
    public suspend fun list(): List<FineTuneInfo> {
        val response = client.http.get("https://api.openai.com/v1/fine-tunes")
        val body = response.body<ListWrapper<FineTuneInfo>>()

        return body.data
    }

    /**
     * [Retrieve fine-tune](https://platform.openai.com/docs/api-reference/fine-tunes/retrieve)
     */
    public suspend fun retrieve(fineTuneId: String): FineTuneInfo {
        val response = client.http.get("https://api.openai.com/v1/fine-tunes/${fineTuneId}")

        return response.body()
    }

    /**
     * [Cancel fine-tune](https://platform.openai.com/docs/api-reference/fine-tunes/cancel)
     */
    public suspend fun cancel(fineTuneId: String): FineTuneInfo {
        val response = client.http.post("https://api.openai.com/v1/fine-tunes/${fineTuneId}/cancel")

        return response.body()
    }

    /**
     * [List fine-tune events](https://platform.openai.com/docs/api-reference/fine-tunes/events)
     */
    public suspend fun events(fineTuneId: String, stream: Boolean = false): List<FineTuneEvent> {
        val response = client.http.get("https://api.openai.com/v1/fine-tunes/${fineTuneId}/events") {
            parameter("stream", stream)
        }
        val body = response.body<ListWrapper<FineTuneEvent>>()

        return body.data
    }
}