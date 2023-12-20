package xyz.cssxsh.openai.model

import io.ktor.client.call.*
import io.ktor.client.request.*
import xyz.cssxsh.openai.*

/**
 * [Models](https://platform.openai.com/docs/api-reference/models)
 */
public class ModelController(private val client: OpenAiClient) {
    public val url: String = "${client.config.api}/models"

    /**
     * [List models](https://platform.openai.com/docs/api-reference/models/list)
     */
    public suspend fun list(): List<ModelInfo> {
        val response = client.http.get(url)
        val body = response.body<ListWrapper<ModelInfo>>()

        return body.data
    }

    /**
     * [Retrieve model](https://platform.openai.com/docs/api-reference/models/retrieve)
     */
    public suspend fun retrieve(model: String): ModelInfo {
        val response = client.http.get("$url/$model")

        return response.body()
    }

    /**
     * [Delete fine-tune model](https://platform.openai.com/docs/api-reference/models/delete)
     */
    public suspend fun cancel(model: String): ModelInfo {
        val response = client.http.delete("$url/$model")

        return response.body()
    }
}