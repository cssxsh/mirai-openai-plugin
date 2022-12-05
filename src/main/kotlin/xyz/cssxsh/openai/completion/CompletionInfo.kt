package xyz.cssxsh.openai.completion

import kotlinx.serialization.*
import xyz.cssxsh.openai.*

@Serializable
public data class CompletionInfo(
    @SerialName("choices")
    val choices: List<Choice> = emptyList(),
    @SerialName("created")
    val created: Long = 0,
    @SerialName("id")
    val id: String = "",
    @SerialName("model")
    val model: String = "",
    @SerialName("object")
    val type: String = "",
    @SerialName("usage")
    val usage: Usage = Usage()
)