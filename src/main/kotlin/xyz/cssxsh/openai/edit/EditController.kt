package xyz.cssxsh.openai.edit

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import xyz.cssxsh.openai.*

/**
 * [Edits](https://beta.openai.com/docs/api-reference/edits)
 */
public class EditController(private val client: OpenAiClient) {

    /**
     * [Create edit](https://beta.openai.com/docs/api-reference/edits/create)
     */
    public suspend fun create(request: EditRequest): EditInfo {
        val response = client.http.post("https://api.openai.com/v1/edits") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }

        return response.body()
    }
}