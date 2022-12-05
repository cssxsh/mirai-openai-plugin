package xyz.cssxsh.openai.moderation

import kotlinx.serialization.*

@Serializable
public data class ModerationInfo(
    @SerialName("id")
    val id: String = "",
    @SerialName("model")
    val model: String = "",
    @SerialName("results")
    val results: List<ModerationResult> = emptyList()
)