package xyz.cssxsh.openai

import kotlinx.serialization.json.JsonObject
import xyz.cssxsh.openai.completion.*
import xyz.cssxsh.openai.edit.EditRequest
import xyz.cssxsh.openai.embedding.EmbeddingRequest
import xyz.cssxsh.openai.finetune.FineTuneRequest
import xyz.cssxsh.openai.image.ImageRequest

@DslMarker
public annotation class OpenAiDsl

public inline fun buildCompletionRequest(
    model: String,
    block: CompletionRequest.Builder.() -> Unit
): CompletionRequest {
    return CompletionRequest.Builder(model = model).apply(block).build()
}

public inline fun buildEditRequest(
    model: String,
    block: EditRequest.Builder.() -> Unit
): EditRequest {
    return EditRequest.Builder(model = model).apply(block).build()
}

public inline fun buildImageRequest(
    prompt: String,
    block: ImageRequest.Builder.() -> Unit
): ImageRequest {
    return ImageRequest.Builder(prompt = prompt).apply(block).build()
}

public inline fun buildEmbeddingRequest(
    model: String,
    block: EmbeddingRequest.Builder.() -> Unit
): EmbeddingRequest {
    return EmbeddingRequest.Builder(model = model).apply(block).build()
}

public inline fun buildFineTuneRequest(
    trainingFile: String,
    block: FineTuneRequest.Builder.() -> Unit
): FineTuneRequest {
    return FineTuneRequest.Builder(trainingFile = trainingFile).apply(block).build()
}

public inline fun buildChoiceFunctionParameters(
    vararg required: String,
    block: ChoiceFunction.Parameters.() -> Unit
): JsonObject {
    return ChoiceFunction.Parameters(required = required).apply(block).build()
}