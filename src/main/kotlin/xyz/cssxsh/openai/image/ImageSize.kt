package xyz.cssxsh.openai.image

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(ImageSize.Serializer::class)
public enum class ImageSize(public val text: String) {
    LARGE("1024x1024"),
    MIDDLE("512x512"),
    SMALL("256x256");

    internal companion object Serializer : KSerializer<ImageSize> {
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor(ImageSize::class.qualifiedName!!, PrimitiveKind.STRING)

        override fun deserialize(decoder: Decoder): ImageSize {
            val text = decoder.decodeString()
            return values().find { it.text == text }
                ?: throw NoSuchElementException("image size: $text")
        }

        override fun serialize(encoder: Encoder, value: ImageSize) {
            encoder.encodeString(value.text)
        }
    }
}