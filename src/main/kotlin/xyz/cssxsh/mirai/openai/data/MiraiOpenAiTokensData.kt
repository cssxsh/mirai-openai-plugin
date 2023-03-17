package xyz.cssxsh.mirai.openai.data

import net.mamoe.mirai.contact.*
import xyz.cssxsh.mirai.economy.*
import xyz.cssxsh.mirai.openai.*
import xyz.cssxsh.mirai.openai.config.*

public object MiraiOpenAiTokensData {

    @PublishedApi
    internal val economy: Boolean by lazy {
        MiraiOpenAiConfig.economy && try {
            EconomyService.register(MiraiOpenAiTokens)
            true
        } catch (_: Throwable) {
            false
        }
    }

    public fun balance(user: User): Int {
        if (economy.not()) return Int.MAX_VALUE
        val account = EconomyService.account(user)
        return with(EconomyService.custom(MiraiOpenAiPlugin)) {
            account[MiraiOpenAiTokens]
        }.toInt()
    }

    public fun plusAssign(user: User, quantity: Int) {
        if (economy.not()) return
        val account = EconomyService.account(user)
        with(EconomyService.custom(MiraiOpenAiPlugin)) {
            account.plusAssign(MiraiOpenAiTokens, quantity.toDouble())
        }
    }

    public fun minusAssign(user: User, quantity: Int) {
        if (economy.not()) return
        val account = EconomyService.account(user)
        with(EconomyService.custom(MiraiOpenAiPlugin)) {
            account.minusAssign(MiraiOpenAiTokens, quantity.toDouble())
        }
    }

    public fun set(user: User, quantity: Int) {
        if (economy.not()) return
        val account = EconomyService.account(user)
        with(EconomyService.custom(MiraiOpenAiPlugin)) {
            account[MiraiOpenAiTokens] = quantity.toDouble()
        }
    }
}