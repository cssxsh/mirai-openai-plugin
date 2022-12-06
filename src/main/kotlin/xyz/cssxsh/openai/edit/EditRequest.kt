package xyz.cssxsh.openai.edit

import kotlinx.serialization.*
import xyz.cssxsh.openai.*

/**
 * @param model [create-model](https://beta.openai.com/docs/api-reference/edits/create#edits/create-model)
 * @param input [create-input](https://beta.openai.com/docs/api-reference/edits/create#edits/create-input)
 * @param instruction [create-suffix](https://beta.openai.com/docs/api-reference/edits/create#edits/create-instruction)
 * @param number [create-n](https://beta.openai.com/docs/api-reference/edits/create#edits/create-n)
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
    val number: Int = 1,
    @SerialName("temperature")
    val temperature: Double = 1.0,
    @SerialName("top_p")
    val topP: Double = 1.0
) {
    public class Builder(@property:OpenAiDsl public var model: String) {
        @OpenAiDsl
        public fun model(value: String): Builder = apply {
            model = value
        }

        @OpenAiDsl
        private var input: String = ""
        @OpenAiDsl
        public fun input(value: String): Builder = apply {
            input = value
        }

        @OpenAiDsl
        public var instruction: String = ""
        @OpenAiDsl
        public fun instruction(value: String): Builder = apply {
            instruction = value
        }

        @OpenAiDsl
        public var number: Int = 1
        @OpenAiDsl
        public fun number(value: Int): Builder = apply {
            number = value
        }

        @OpenAiDsl
        public var temperature: Double = 1.0
        @OpenAiDsl
        public fun temperature(value: Double): Builder = apply {
            temperature = value
        }

        @OpenAiDsl
        public var topP: Double = 1.0
        @OpenAiDsl
        public fun topP(value: Double): Builder = apply {
            topP = value
        }

        public fun build(): EditRequest {
            require(instruction.isNotEmpty()) { "Required instruction" }
            return EditRequest(
                model = model,
                input = input,
                instruction = instruction,
                number = number,
                temperature = temperature,
                topP = topP
            )
        }
    }
}