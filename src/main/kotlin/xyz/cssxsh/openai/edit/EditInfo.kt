package xyz.cssxsh.openai.edit

import kotlinx.serialization.*
import xyz.cssxsh.openai.*

@Serializable
public data class EditInfo(
    @SerialName("choices")
    val choices: List<Choice> = emptyList(),
    @SerialName("created")
    val created: Long = 0,
    @SerialName("object")
    val type: String = "",
    @SerialName("usage")
    val usage: Usage = Usage()
)