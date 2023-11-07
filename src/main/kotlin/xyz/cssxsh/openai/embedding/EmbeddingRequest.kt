package xyz.cssxsh.openai.embedding

import kotlinx.serialization.*
import xyz.cssxsh.openai.*

/**
 * @param input [embeddings-create-input](https://platform.openai.com/docs/api-reference/embeddings/create#embeddings-create-input)
 * @param model [embeddings-create-model](https://platform.openai.com/docs/api-reference/embeddings/create#embeddings-create-model)
 * @param format [embeddings-create-encoding_format](https://platform.openai.com/docs/api-reference/embeddings/create#embeddings-create-encoding_format) float | base64
 * @param user [embeddings-create-user](https://platform.openai.com/docs/api-reference/embeddings/create#embeddings-create-user)
 */
@Serializable
public data class EmbeddingRequest(
    @SerialName("input")
    val input: List<String>,
    @SerialName("model")
    val model: String,
    @SerialName("encoding_format")
    val format: String = "float",
    @SerialName("user")
    val user: String = ""
) {
    public class Builder(@property:OpenAiDsl public var model: String) {
        @OpenAiDsl
        public fun model(value: String): Builder = apply {
            model = value
        }

        @OpenAiDsl
        public var input: List<String> = emptyList()

        @OpenAiDsl
        public fun input(values: List<String>): Builder = apply {
            input = values
        }

        @OpenAiDsl
        public fun input(vararg values: String): Builder = apply {
            input = values.asList()
        }

        @OpenAiDsl
        public var user: String = ""

        @OpenAiDsl
        public fun user(value: String): Builder = apply {
            user = value
        }

        public fun build(): EmbeddingRequest {
            require(input.isNotEmpty()) { "Required input" }
            return EmbeddingRequest(
                input = input,
                model = model,
                user = user,
            )
        }
    }
}