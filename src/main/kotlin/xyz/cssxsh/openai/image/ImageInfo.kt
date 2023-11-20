package xyz.cssxsh.openai.image

import kotlinx.serialization.*

@Serializable
public data class ImageInfo(
    @SerialName("created")
    val created: Long = 0,
    @SerialName("data")
    val data: List<Data> = emptyList()
) {
    @Serializable
    public data class Data(
        @SerialName("url")
        val url: String = "",
        @SerialName("b64_json")
        val base64: String = "",
        @SerialName("revised_prompt")
        var revisedPrompt:String = ""
    )
}