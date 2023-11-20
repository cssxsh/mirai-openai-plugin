package xyz.cssxsh.openai.image

import kotlinx.serialization.*
import xyz.cssxsh.openai.*

/**
 * @param prompt [images-create-prompt](https://platform.openai.com/docs/api-reference/images/create#images-create-prompt)
 * @param model [images-create-model](https://platform.openai.com/docs/api-reference/images/create#images-create-model) dall-e-2 | dall-e-3
 * @param number [images-create-n](https://platform.openai.com/docs/api-reference/images/create#images-create-n)
 * @param quality [images-create-quality](https://platform.openai.com/docs/api-reference/images/create#images-create-quality) standard | hd
 * @param format [images-create-response_format](https://platform.openai.com/docs/api-reference/images/create#images-create-response_format)
 * @param size [images-create-size](https://platform.openai.com/docs/api-reference/images/create#images-create-size)
 * @param style [images-create-style](https://platform.openai.com/docs/api-reference/images/create#images-create-style) vivid | natural
 * @param user [images-create-user](https://platform.openai.com/docs/api-reference/images/create#images-create-user)
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

        @OpenAiDsl
        public var model: String = "dall-e-2"

        @OpenAiDsl
        public fun model(value: String): Builder = apply {
            model = value
        }

        public fun build(): ImageRequest {
            require(number in 1..10) { "Must be between 1 and 10" }
            // 这里添加对模型和尺寸的检查
            validateSizeForModel(model, size)
            return ImageRequest(
                prompt = prompt,
                number = number,
                size = size,
                format = format,
                user = user,
                model = model,
            )
        }

        private fun validateSizeForModel(model: String, size: ImageSize) {
            when (model) {
                "dall-e-2" -> require(size in listOf(ImageSize.SMALL, ImageSize.MIDDLE, ImageSize.LARGE)) {
                    "dall-e-2 model requires one of the sizes: SMALL (256x256), MIDDLE (512x512), or LARGE (1024x1024)"
                }

                "dall-e-3" -> require(size in listOf(ImageSize.LARGE, ImageSize.LARGE_WIDTH, ImageSize.LARGE_HEIGHT)) {
                    "dall-e-3 model requires one of the sizes: LARGE (1024x1024), LARGE_WIDTH (1792x1024), or LARGE_HEIGHT (1024x1792)"
                }

                else -> throw IllegalArgumentException("Unsupported model: $model")
            }
        }

    }
}