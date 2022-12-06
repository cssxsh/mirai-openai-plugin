package xyz.cssxsh.mirai.openai

import kotlinx.coroutines.*
import net.mamoe.mirai.event.*
import net.mamoe.mirai.mock.*
import org.junit.jupiter.api.*

internal class MiraiOpenAiListenerTest {
    private val bot = MockBotFactory.newMockBotBuilder().create()
    private val group = bot.addGroup(114514, "mock")
    private val sender = group.addMember(1919810, "...")

    init {
        MiraiOpenAiListener.registerTo(GlobalEventChannel)
    }

    @Test
    fun completions(): Unit = runBlocking {
        sender.says("> 你好")
    }
}