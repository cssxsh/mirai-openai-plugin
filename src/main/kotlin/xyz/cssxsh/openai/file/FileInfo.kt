package xyz.cssxsh.openai.file

import kotlinx.serialization.*

@Serializable
public data class FileInfo(
    @SerialName("bytes")
    val bytes: Int = 0,
    @SerialName("created_at")
    val createdAt: Long = 0,
    @SerialName("filename")
    val filename: String = "",
    @SerialName("id")
    val id: String = "",
    @SerialName("object")
    val type: String = "",
    @SerialName("purpose")
    val purpose: String = "",
    @SerialName("deleted")
    val deleted: Boolean = false
)