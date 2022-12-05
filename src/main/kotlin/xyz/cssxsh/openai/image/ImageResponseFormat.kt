package xyz.cssxsh.openai.image

import kotlinx.serialization.*

@Serializable
public enum class ImageResponseFormat {
    URL,
    B64_JSON;
}