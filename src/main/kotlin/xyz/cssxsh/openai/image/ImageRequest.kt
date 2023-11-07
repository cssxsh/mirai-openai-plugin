package xyz.cssxsh.openai.image

import kotlinx.serialization.*
import xyz.cssxsh.openai.*

/**
 * @param prompt [create-prompt](https://platform.openai.com/docs/api-reference/images/create#images-create-prompt)
 * @param model [create-model](https://platform.openai.com/docs/api-reference/images/create#images-create-model) dall-e-2 / dall-e-3
 * @param number [create-n](https://platform.openai.com/docs/api-reference/images/create#images-create-n)
 * @param quality [create-quality](https://platform.openai.com/docs/api-reference/images/create#images-create-quality) standard / hd
 * @param format [create-response_format](https://platform.openai.com/docs/api-reference/images/create#images-create-response_format)
 * @param size [create-size](https://platform.openai.com/docs/api-reference/images/create#images-create-size)
 * @param style [create-style](https://platform.openai.com/docs/api-reference/images/create#images-create-style) vivid / natural
 * @param user [create-user](https://platform.openai.com/docs/api-reference/images/create#images-create-user)
 */
@Serializable
public data class ImageRequest(
    @SerialName("prompt")
    val prompt: String,
    @SerialName("model")
    val model: String = "dall-e-2",
    @SerialName("n")
    val number: Int = 1,
    @SerialName("quality")
    val quality: String = "standard",
    @SerialName("response_format")
    val format: ImageResponseFormat = ImageResponseFormat.URL,
    @SerialName("size")
    val size: ImageSize = ImageSize.LARGE,
    @SerialName("style")
    val style: String? = "vivid",
    @SerialName("user")
    val user: String = ""
) {
    public class Builder(@property:OpenAiDsl public var prompt: String) {
        @OpenAiDsl
        public fun prompt(value: String): Builder = apply {
            prompt = value
        }

        @OpenAiDsl
        public var number: Int = 1

        @OpenAiDsl
        public fun number(value: Int): Builder = apply {
            number = value
        }

        @OpenAiDsl
        public var size: ImageSize = ImageSize.LARGE

        @OpenAiDsl
        public fun size(value: ImageSize): Builder = apply {
            size = value
        }

        @OpenAiDsl
        public var format: ImageResponseFormat = ImageResponseFormat.URL

        @OpenAiDsl
        public fun format(value: ImageResponseFormat): Builder = apply {
            format = value
        }

        @OpenAiDsl
        public var user: String = ""

        @OpenAiDsl
        public fun user(value: String): Builder = apply {
            user = value
        }

        public fun build(): ImageRequest {
            require(number in 1..10) { "Must be between 1 and 10" }
            return ImageRequest(
                prompt = prompt,
                number = number,
                size = size,
                format = format,
                user = user,
            )
        }
    }
}