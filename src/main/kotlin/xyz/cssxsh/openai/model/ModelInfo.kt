package xyz.cssxsh.openai.model

import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
public data class ModelInfo(
    @SerialName("id")
    val id: String = "",
    @SerialName("object")
    val type: String = "",
    @SerialName("created")
    val created: Long = 0,
    @SerialName("owned_by")
    val ownedBy: String = "",
    @SerialName("permission")
    val permission: List<ModelPermission> = emptyList(),
    @SerialName("root")
    val root: String? = null,
    @SerialName("parent")
    val parent: String? = null
)