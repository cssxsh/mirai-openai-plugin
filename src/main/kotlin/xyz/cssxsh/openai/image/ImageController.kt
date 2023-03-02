package xyz.cssxsh.openai.image

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import xyz.cssxsh.openai.*

/**
 * [Images](https://platform.openai.com/docs/api-reference/images)
 */
public class ImageController(private val client: OpenAiClient) {

    /**
     * [Create image](https://platform.openai.com/docs/api-reference/images/create)
     */
    public suspend fun create(request: ImageRequest): ImageInfo {
        val response = client.http.post("https://api.openai.com/v1/images/generations") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }

        return response.body()
    }

    /**
     * [Create image](https://platform.openai.com/docs/api-reference/images/create)
     */
    public suspend fun create(prompt: String, block: ImageRequest.Builder.() -> Unit): ImageInfo {
        return create(request = ImageRequest.Builder(prompt = prompt).apply(block).build())
    }

    /**
     * [Create image edit](https://platform.openai.com/docs/api-reference/images/create-edit)
     */
    public suspend fun createEdit(image: InputProvider, mask: InputProvider? = null, request: ImageRequest): ImageInfo {
        val response = client.http.submitFormWithBinaryData("https://api.openai.com/v1/images/edits", formData {
            append("image", image)
            if (mask != null) append("mask", mask)
            append("prompt", request.prompt)
            append("n", request.number)
            append("size", request.size.text)
            append("response_format", request.format.name.lowercase())
            if (request.user.isNotEmpty()) append("user", request.user)
        })

        return response.body()
    }

    /**
     * [Create image edit](https://platform.openai.com/docs/api-reference/images/create-edit)
     */
    public suspend fun createEdit(
        image: InputProvider,
        mask: InputProvider? = null,
        prompt: String,
        block: ImageRequest.Builder.() -> Unit
    ): ImageInfo {
        return createEdit(
            image = image,
            mask = mask,
            request = ImageRequest.Builder(prompt = prompt).apply(block).build()
        )
    }

    /**
     * [Create image variation](https://platform.openai.com/docs/api-reference/images/create-variation)
     */
    public suspend fun createVariation(image: InputProvider, request: ImageRequest): ImageInfo {
        val response = client.http.submitFormWithBinaryData("https://api.openai.com/v1/images/variations", formData {
            append("image", image)
            append("n", request.number)
            append("size", request.size.text)
            append("response_format", request.format.name.lowercase())
            if (request.user.isNotEmpty()) append("user", request.user)
        })

        return response.body()
    }

    /**
     * [Create image variation](https://platform.openai.com/docs/api-reference/images/create-variation)
     */
    public suspend fun createVariation(
        image: InputProvider,
        prompt: String,
        block: ImageRequest.Builder.() -> Unit
    ): ImageInfo {
        return createEdit(
            image = image,
            request = ImageRequest.Builder(prompt = prompt).apply(block).build()
        )
    }
}