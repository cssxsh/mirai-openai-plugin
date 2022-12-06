package xyz.cssxsh.openai.image

import kotlinx.serialization.*

@Serializable
public enum class ImageResponseFormat {
    @SerialName("url")
    URL,
    @SerialName("b64_json")
    B64_JSON;
}