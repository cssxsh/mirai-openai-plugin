package xyz.cssxsh.openai.moderation

import kotlinx.serialization.*

@Serializable
public data class ModerationResult(
    @SerialName("categories")
    val categories: Map<String, Boolean> = emptyMap(),
    @SerialName("category_scores")
    val categoryScores: Map<String, Double> = emptyMap(),
    @SerialName("flagged")
    val flagged: Boolean = false
)