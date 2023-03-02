package xyz.cssxsh.openai.chat

import kotlinx.serialization.*
import xyz.cssxsh.openai.*

@Serializable
public data class ChatInfo(
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