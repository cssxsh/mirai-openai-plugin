package xyz.cssxsh.openai.moderation

import kotlinx.serialization.*

/**
 * @param input [moderations-create-input](https://platform.openai.com/docs/api-reference/moderations/create#moderations-create-input)
 * @param model [moderations-create-model](https://platform.openai.com/docs/api-reference/moderations/create#moderations-create-model) text-moderation-stable | text-moderation-latest
 */
@Serializable
public data class ModerationRequest(
    @SerialName("input")
    val input: String,
    @SerialName("model")
    val model: String = "text-moderation-latest"
)