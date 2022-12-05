package xyz.cssxsh.openai.model

import kotlinx.serialization.*

@Serializable
public data class ModelPermission(
    @SerialName("allow_create_engine")
    val allowCreateEngine: Boolean = false,
    @SerialName("allow_fine_tuning")
    val allowFineTuning: Boolean = false,
    @SerialName("allow_logprobs")
    val allowLogprobs: Boolean = false,
    @SerialName("allow_sampling")
    val allowSampling: Boolean = false,
    @SerialName("allow_search_indices")
    val allowSearchIndices: Boolean = false,
    @SerialName("allow_view")
    val allowView: Boolean = false,
    @SerialName("created")
    val created: Long = 0,
    @SerialName("group")
    val group: String? = null,
    @SerialName("id")
    val id: String = "",
    @SerialName("is_blocking")
    val isBlocking: Boolean = false,
    @SerialName("object")
    val type: String = "",
    @SerialName("organization")
    val organization: String = ""
)