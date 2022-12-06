package xyz.cssxsh.mirai.openai.config

import net.mamoe.mirai.console.data.*
import xyz.cssxsh.openai.completion.*
import xyz.cssxsh.openai.image.*

@PublishedApi
internal object ImageConfig : ReadOnlyPluginConfig("image") {

    @ValueName("n")
    val number: Int by value(1)

    @ValueName("size")
    val size: ImageSize by value(ImageSize.LARGE)

    @ValueName("format")
    val format: ImageResponseFormat by value(ImageResponseFormat.URL)

    fun push(builder: ImageRequest.Builder) {
        builder.number = number
        builder.size = size
        builder.number = number
        builder.size = size
        builder.format = format
    }
}