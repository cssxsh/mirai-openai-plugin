package xyz.cssxsh.openai.embedding

import kotlinx.serialization.*

@Serializable
public data class EmbeddingInfo(
    @SerialName("embedding")
    val embedding: List<Double> = emptyList(),
    @SerialName("index")
    val index: Int = 0,
    @SerialName("object")
    val type: String = ""
)