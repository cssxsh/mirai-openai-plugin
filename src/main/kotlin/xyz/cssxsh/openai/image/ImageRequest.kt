package xyz.cssxsh.openai.image

import kotlinx.serialization.*

/**
 * @param prompt [create-prompt](https://beta.openai.com/docs/api-reference/images/create#images/create-prompt)
 * @param n [create-input](https://beta.openai.com/docs/api-reference/images/create#images/create-n)
 * @param size [create-size](https://beta.openai.com/docs/api-reference/images/create#images/create-size)
 * @param format [create-response_format](https://beta.openai.com/docs/api-reference/images/create#images/create-response_format)
 * @param user [create-user](https://beta.openai.com/docs/api-reference/images/create#images/create-user)
 */
@Serializable
public data class ImageRequest(
    @SerialName("prompt")
    val prompt: String,
    @SerialName("n")
    val n: Int = 1,
    @SerialName("size")
    val size: ImageSize = ImageSize.LARGE,
    @SerialName("response_format")
    val format: ImageResponseFormat = ImageResponseFormat.URL,
    @SerialName("user")
    val user: String = ""
)