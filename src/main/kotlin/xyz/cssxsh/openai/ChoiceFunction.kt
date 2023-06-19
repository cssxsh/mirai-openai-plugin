package xyz.cssxsh.openai

import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
public data class ChoiceFunction(
    @SerialName("name")
    public val name: String,
    @SerialName("description")
    public val description: String? = null,
    @SerialName("parameters")
    public val parameters: JsonObject? = null,
) {

    public class Parameters(vararg required: String) {

        @OpenAiDsl
        public var required: List<String> = required.asList()

        @OpenAiDsl
        public fun required(values: List<String>): Parameters = apply {
            required = values
        }

        @OpenAiDsl
        public fun required(vararg values: String): Parameters = apply {
            required = values.asList()
        }

        @OpenAiDsl
        public fun messages(block: MutableList<String>.() -> Unit): Parameters = apply {
            required = buildList(block)
        }

        @OpenAiDsl
        public var properties: Map<String, JsonObject> = emptyMap()

        @OpenAiDsl
        public fun properties(block: MutableMap<String, JsonObject>.() -> Unit): Parameters = apply {
            properties = buildMap(block)
        }

        @OpenAiDsl
        public fun property(name: String, block: JsonObjectBuilder.() -> Unit): Parameters = apply {
            properties += (name to buildJsonObject(block))
        }

        public fun build(): JsonObject {
            return buildJsonObject {
                put("type", "object")
                putJsonObject("properties") {
                    for ((name, property) in properties) {
                        put(name, property)
                    }
                }
                putJsonArray("required") {
                    for (name in required) {
                        add(name)
                    }
                }
            }
        }
    }
}