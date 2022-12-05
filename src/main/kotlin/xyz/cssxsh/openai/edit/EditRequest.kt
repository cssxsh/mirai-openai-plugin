package xyz.cssxsh.openai.edit

import kotlinx.serialization.*

/**
 * @param model [create-model](https://beta.openai.com/docs/api-reference/edits/create#edits/create-model)
 * @param input [create-input](https://beta.openai.com/docs/api-reference/edits/create#edits/create-input)
 * @param instruction [create-suffix](https://beta.openai.com/docs/api-reference/edits/create#edits/create-instruction)
 * @param n [create-n](https://beta.openai.com/docs/api-reference/edits/create#edits/create-n)
 * @param temperature [create-temperature](https://beta.openai.com/docs/api-reference/edits/create#edits/create-temperature)
 * @param topP [create-top_p](https://beta.openai.com/docs/api-reference/edits/create#edits/create-top_p)
 */
@Serializable
public data class EditRequest(
    @SerialName("model")
    val model: String,
    @SerialName("input")
    val input: String = "",
    @SerialName("instruction")
    val instruction: String,
    @SerialName("n")
    val n: Int = 1,
    @SerialName("temperature")
    val temperature: Double = 1.0,
    @SerialName("top_p")
    val topP: Double = 1.0
)