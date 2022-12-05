package xyz.cssxsh.openai.finetune

import kotlinx.serialization.*

@Serializable
public data class FineTuneEvent(
    @SerialName("created_at")
    val createdAt: Long = 0,
    @SerialName("level")
    val level: String = "",
    @SerialName("message")
    val message: String = "",
    @SerialName("object")
    val type: String = ""
)