package xyz.cssxsh.openai.completion

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import xyz.cssxsh.openai.*

/**
 * [Completions](https://beta.openai.com/docs/api-reference/completions)
 */
public class CompletionController(private val client: OpenAiClient) {

    /**
     * [Create completion](https://beta.openai.com/docs/api-reference/completions/create#completions/create)
     */
    public suspend fun create(request: CompletionRequest): CompletionInfo {
        val response = client.http.post("https://api.openai.com/v1/completions") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }

        return response.body()
    }
}