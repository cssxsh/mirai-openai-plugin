package xyz.cssxsh.openai

import kotlinx.serialization.*

/**
 * @param role either “system”, “user”, or “assistant”
 */
@Serializable
public data class ChoiceMessage(
    @SerialName("role")
    val role: String,
    @SerialName("content")
    val content: String
)