package xyz.cssxsh.openai

import kotlinx.coroutines.*
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
}