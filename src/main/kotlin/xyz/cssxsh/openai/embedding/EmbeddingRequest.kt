package xyz.cssxsh.openai.embedding

import kotlinx.serialization.*
import xyz.cssxsh.openai.*

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
    val input: List<String>,
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
                model = model,
                input = input,
                user = user,
            )
        }
    }
}