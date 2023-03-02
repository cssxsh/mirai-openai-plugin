package xyz.cssxsh.openai

import kotlinx.serialization.*

@Serializable
public data class Choice(
    @SerialName("finish_reason")
    val finishReason: String? = null,
    @SerialName("index")
    val index: Int = 0,
    @SerialName("logprobs")
    val logprobs: Int? = null,
    @SerialName("text")
    val text: String = ""
)