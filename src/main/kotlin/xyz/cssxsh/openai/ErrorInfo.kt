package xyz.cssxsh.openai

import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
public data class ErrorInfo(
    @SerialName("code")
    val code: String? = null,
    @SerialName("message")
    val message: JsonElement = JsonNull,
    @SerialName("param")
    val param: JsonElement = JsonNull,
    @SerialName("type")
    val type: String = ""
)