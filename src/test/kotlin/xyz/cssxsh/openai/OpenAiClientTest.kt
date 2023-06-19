package xyz.cssxsh.openai

import kotlinx.coroutines.*
import kotlinx.serialization.json.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.condition.*
import xyz.cssxsh.openai.image.*

internal class OpenAiClientTest {
    init {
        System.setProperty("xyz.cssxsh.openai.cname", "false")
    }
    private val config = object : OpenAiClientConfig {
        override val proxy: String = ""
        override val doh: String = "https://public.dns.iij.jp/dns-query"
        override val ipv6: Boolean = true
        override val timeout: Long = 60_000L
        override val token: String = System.getenv("OPENAI_TOKEN")
    }
    private val client = OpenAiClient(config = config)

    @Test
    fun models(): Unit = runBlocking {
        val models = client.model.list()
        val random = models.random()
        val model = client.model.retrieve(model = random.id)

        Assertions.assertEquals(random.id, model.id)
        Assertions.assertEquals(random, model)
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "CI", matches = "true")
    fun completions(): Unit = runBlocking {
        val model = "text-davinci-003"
        val completion = client.completion.create(model) {
            prompt("你好")
            maxTokens(256)
        }

        Assertions.assertEquals(model, completion.model)
        Assertions.assertFalse(completion.choices.isEmpty())
    }

    @Test
    fun images(): Unit = runBlocking {
        val prompt = "风景图"
        val image = client.image.create(prompt) {
            format = ImageResponseFormat.URL
        }

        Assertions.assertFalse(image.data.isEmpty())
        Assertions.assertTrue(image.data.first().url.isNotEmpty())
    }

    @Test
    fun files(): Unit = runBlocking {
        val files = client.file.list()
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "CI", matches = "true")
    fun chat(): Unit = runBlocking {
        val model = "gpt-3.5-turbo"
        val chat = client.chat.create(model) {
            messages {
                user(content = "Hello!")
            }
        }

        Assertions.assertFalse(chat.choices.isEmpty())
        Assertions.assertNotNull(chat.choices.first().message)
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "CI", matches = "true")
    fun call(): Unit = runBlocking {
        val model = "gpt-3.5-turbo-0613"
        var cache: List<ChoiceMessage> = emptyList()
        var func: List<ChoiceFunction> = emptyList()
        val step1 = client.chat.create(model) {
            messages {
                system("Don't make assumptions about what values to plug into functions. Ask for clarification if a user request is ambiguous.")
                user("What's the weather like today")
            }
            functions {
                define("get_current_weather", "Get the current weather") {
                    property("location") {
                        put("type", "string")
                        put("description", "The city and state, e.g. San Francisco, CA")
                    }
                    property("format") {
                        put("type", "string")
                        putJsonArray("enum") {
                            add("celsius")
                            add("fahrenheit")
                        }
                        put("description", "The temperature unit to use. Infer this from the users location.")
                    }
                    required("location", "format")
                }
                define("get_n_day_weather_forecast", "Get an N-day weather forecast") {
                    property("location") {
                        put("type", "string")
                        put("description", "The city and state, e.g. San Francisco, CA")
                    }
                    property("format") {
                        put("type", "string")
                        putJsonArray("enum") {
                            add("celsius")
                            add("fahrenheit")
                        }
                        put("description", "The temperature unit to use. Infer this from the users location.")
                    }
                    property("num_days") {
                        put("type", "integer")
                        put("description", "The number of days to forecast")
                    }
                    required("location", "format", "num_days")
                }
            }
            maxTokens = 2048

            cache = messages
            func = functions
        }

        Assertions.assertFalse(step1.choices.isEmpty())

        val step2 = client.chat.create(model) {
            messages {
                addAll(cache)
                add(step1.choices.first().message!!)

                user("I'm in Glasgow, Scotland.")
            }
            functions = func
            maxTokens = 2048
        }

        Assertions.assertFalse(step2.choices.isEmpty())
        val assistant = step2.choices.first()
        Assertions.assertEquals("function_call", assistant.finishReason)
        Assertions.assertFalse(assistant.message!!.call!!.isEmpty())
    }
}