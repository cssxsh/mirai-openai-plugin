package xyz.cssxsh.openai.embedding

import kotlinx.serialization.*

/**
 * @param model [create-model](https://beta.openai.com/docs/api-reference/embeddings/create#embeddings/create-model)
 * @param input [create-input](https://beta.openai.com/docs/api-reference/embeddings/create#embeddings/create-input)
 * @param user [create-user](https://beta.openai.com/docs/api-reference/embeddings/create#embeddings/create-user)
 */
@Serializable
public data class EmbeddingRequest(
    @SerialName("model")
    val model: String,
    @SerialName("input")
    val input: String = "",
    @SerialName("user")
    val user: String = ""
)