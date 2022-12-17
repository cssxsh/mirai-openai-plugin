package xyz.cssxsh.openai.image

import kotlinx.serialization.*
import xyz.cssxsh.openai.*

/**
 * @param prompt [create-prompt](https://beta.openai.com/docs/api-reference/images/create#images/create-prompt)
 * @param number [create-input](https://beta.openai.com/docs/api-reference/images/create#images/create-n)
 * @param size [create-size](https://beta.openai.com/docs/api-reference/images/create#images/create-size)
 * @param format [create-response_format](https://beta.openai.com/docs/api-reference/images/create#images/create-response_format)
 * @param user [create-user](https://beta.openai.com/docs/api-reference/images/create#images/create-user)
 */
@Serializable
public data class ImageRequest(
    @SerialName("prompt")
    val prompt: String,
    @SerialName("n")
    val number: Int = 1,
    @SerialName("size")
    val size: ImageSize = ImageSize.LARGE,
    @SerialName("response_format")
    val format: ImageResponseFormat = ImageResponseFormat.URL,
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