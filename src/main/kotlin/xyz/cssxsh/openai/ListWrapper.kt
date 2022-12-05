package xyz.cssxsh.openai

import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
internal data class ListWrapper<T : Any>(
    @SerialName("data")
    val data: List<T>,
    @SerialName("usage")
    val usage: Usage = Usage(),
    @SerialName("object")
    val type: String
)