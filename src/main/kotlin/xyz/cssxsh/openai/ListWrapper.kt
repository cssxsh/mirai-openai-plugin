package xyz.cssxsh.openai

import kotlinx.serialization.*

@Serializable
internal data class ListWrapper<T : Any>(
    @SerialName("has_more")
    val more: Boolean = false,
    @SerialName("data")
    val data: List<T>,
    @SerialName("usage")
    val usage: Usage = Usage(),
    @SerialName("object")
    val type: String
)