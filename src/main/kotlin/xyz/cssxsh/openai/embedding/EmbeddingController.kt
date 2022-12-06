package xyz.cssxsh.openai.embedding

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import xyz.cssxsh.openai.*

/**
 * [Embeddings](https://beta.openai.com/docs/api-reference/embeddings)
 */
public class EmbeddingController(private val client: OpenAiClient) {

    /**
     * [Create embeddings](https://beta.openai.com/docs/api-reference/embeddings/create)
     */
    public suspend fun create(request: EmbeddingRequest): List<EmbeddingInfo> {
        val response = client.http.post("https://api.openai.com/v1/embeddings") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        val body = response.body<ListWrapper<EmbeddingInfo>>()

        return body.data
    }

    /**
     * [Create embeddings](https://beta.openai.com/docs/api-reference/embeddings/create)
     */
    public suspend fun create(model: String, block: EmbeddingRequest.Builder.() -> Unit): List<EmbeddingInfo> {
        return create(request = EmbeddingRequest.Builder(model = model).apply(block).build())
    }
}