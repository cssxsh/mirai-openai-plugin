package xyz.cssxsh.openai

import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
public data class ChoiceFunction(
    @SerialName("name")
    public val name: String,
    @SerialName("description")
    public val description: String,
    @SerialName("parameters")
    public val parameters: JsonObject,
)