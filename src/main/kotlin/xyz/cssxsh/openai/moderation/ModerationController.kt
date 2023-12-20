package xyz.cssxsh.openai.moderation

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import xyz.cssxsh.openai.*

/**
 * [Moderations](https://platform.openai.com/docs/api-reference/moderations)
 */
public class ModerationController(private val client: OpenAiClient) {
    public val url: String = "${client.config.api}/moderations"

    /**
     * [Create moderation](https://platform.openai.com/docs/api-reference/moderations/create)
     */
    public suspend fun create(request: ModerationRequest): ModerationInfo {
        val response = client.http.post(url) {
            contentType(ContentType.Application.Json)
            setBody(request)
        }

        return response.body()
    }

    /**
     * [Create moderation](https://platform.openai.com/docs/api-reference/moderations/create)
     */
    public suspend fun create(input: String, model: String = "text-moderation-latest"): ModerationInfo {
        return create(request = ModerationRequest(input = input, model = model))
    }
}