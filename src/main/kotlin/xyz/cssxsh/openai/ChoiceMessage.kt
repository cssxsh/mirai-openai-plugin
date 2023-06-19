package xyz.cssxsh.openai

import kotlinx.serialization.*
import kotlinx.serialization.json.*

/**
 * @param role either “system”, “user”, “assistant”, or “function”
 */
@Serializable
public data class ChoiceMessage(
    @SerialName("role")
    val role: String,
    @SerialName("content")
    val content: String = "",
    @SerialName("name")
    val name: String? = null,
    @SerialName("function_call")
    val call: JsonObject? = null
)