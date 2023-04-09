package xyz.cssxsh.mirai.openai

import kotlinx.coroutines.*
import net.mamoe.mirai.event.*
import net.mamoe.mirai.mock.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.condition.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class MiraiOpenAiListenerTest {
    init {
        System.setProperty("org.slf4j.simpleLogger.log.xyz", "TRACE")
        System.setProperty("org.slf4j.simpleLogger.log.net.mamoe", "TRACE")
        System.setProperty("xyz.cssxsh.openai.cname", "false")
        MiraiOpenAiListener.registerTo(GlobalEventChannel)
    }
    private val bot = MockBotFactory.newMockBotBuilder().create()
    private val group = bot.addGroup(114514, "mock")
    private val sender = group.addMember(1919810, "...")
    private val chatter = group.addMember(12345, "...")
    private val questioner = group.addMember(7890, "...")

    @Test
    @DisabledIfEnvironmentVariable(named = "CI", matches = "true")
    fun completions(): Unit = runBlocking {
        sender.says("> 你好")
    }

    @Test
    fun chat(): Unit = runBlocking {
        chatter.says("chat")
        delay(1_000)
        chatter.says("你好")
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "CI", matches = "true")
    fun question(): Unit = runBlocking {
        questioner.says("Q&A")
        delay(1_000)
        questioner.says("Rust是什么?")
    }

    @AfterAll
    fun cancel(): Unit = runBlocking {
        MiraiOpenAiListener.cancel()
    }
}