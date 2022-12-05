package xyz.cssxsh.openai.moderation

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import xyz.cssxsh.openai.*

/**
 * [Moderations](https://beta.openai.com/docs/api-reference/moderations)
 */
public class ModerationController(private val client: OpenAiClient) {

    /**
     * [Create moderation](https://beta.openai.com/docs/api-reference/moderations/create)
     */
    public suspend fun create(request: ModerationRequest): ModerationResult {
        val response = client.http.post("https://api.openai.com/v1/images/generations") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }

        return response.body()
    }
}