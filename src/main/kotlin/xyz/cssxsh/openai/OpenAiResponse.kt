package xyz.cssxsh.openai

import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
internal data class OpenAiResponse<T : Any>(
    @SerialName("data")
    val data: T,
    @SerialName("object")
    val type: String
)