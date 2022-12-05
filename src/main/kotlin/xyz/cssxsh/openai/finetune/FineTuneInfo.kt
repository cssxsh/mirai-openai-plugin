package xyz.cssxsh.openai.finetune

import kotlinx.serialization.*
import xyz.cssxsh.openai.file.*

@Serializable
public data class FineTuneInfo(
    @SerialName("created_at")
    val createdAt: Long = 0,
    @SerialName("events")
    val events: List<FineTuneEvent> = emptyList(),
    @SerialName("fine_tuned_model")
    val fineTunedModel: String? = null,
    @SerialName("hyperparams")
    val hyperparams: Hyperparams = Hyperparams(),
    @SerialName("id")
    val id: String = "",
    @SerialName("model")
    val model: String = "",
    @SerialName("object")
    val type: String = "",
    @SerialName("organization_id")
    val organizationId: String = "",
    @SerialName("result_files")
    val resultFiles: List<FileInfo> = emptyList(),
    @SerialName("status")
    val status: String = "",
    @SerialName("training_files")
    val trainingFiles: List<FileInfo> = emptyList(),
    @SerialName("updated_at")
    val updatedAt: Int = 0,
    @SerialName("validation_files")
    val validationFiles: List<FileInfo> = emptyList()
)