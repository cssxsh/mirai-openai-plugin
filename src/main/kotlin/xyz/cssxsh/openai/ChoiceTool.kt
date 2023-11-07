package xyz.cssxsh.openai

import kotlinx.serialization.*

@Serializable
public data class ChoiceTool(
    @SerialName("type")
    public val type: String,
    @SerialName("function")
    public val function: ChoiceFunction? = null
)