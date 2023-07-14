package xyz.cssxsh.openai

public class OpenAiException(public val info: ErrorInfo) : IllegalStateException() {
    override val message: String get() = info.toString()
}