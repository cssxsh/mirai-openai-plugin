package xyz.cssxsh.openai.audio

import kotlinx.serialization.*
import xyz.cssxsh.openai.*

@Serializable
public data class AudioInfo(
    @SerialName("text")
    val text: String
)