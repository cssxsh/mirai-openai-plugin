package xyz.cssxsh.openai

import kotlinx.serialization.*

@Serializable
internal data class ErrorInfoWrapper(
    @SerialName("error")
    val error: ErrorInfo
)