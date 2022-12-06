package xyz.cssxsh.mirai.openai.config

import kotlinx.serialization.modules.*
import net.mamoe.mirai.console.data.*
import xyz.cssxsh.openai.completion.*
import xyz.cssxsh.openai.image.*

@PublishedApi
internal object ImageConfig : ReadOnlyPluginConfig("image") {

    override val serializersModule: SerializersModule = SerializersModule {
        contextual(ImageSize.Serializer)
    }

    @ValueName("n")
    @ValueDescription("generations number")
    val number: Int by value(1)

    @ValueName("size")
    @ValueDescription("1024x1024 512x512 256x256")
    val size: ImageSize by value(ImageSize.LARGE)

    @ValueName("format")
    @ValueDescription("下载方式 url b64_json")
    val format: ImageResponseFormat by value(ImageResponseFormat.URL)

    fun push(builder: ImageRequest.Builder) {
        builder.number = number
        builder.size = size
        builder.format = format
    }
}