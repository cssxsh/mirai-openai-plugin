package xyz.cssxsh.openai.finetune

import kotlinx.serialization.*

@Serializable
public data class Hyperparams(
    @SerialName("batch_size")
    val batchSize: Int = 0,
    @SerialName("learning_rate_multiplier")
    val learningRateMultiplier: Double = 0.0,
    @SerialName("n_epochs")
    val nEpochs: Int = 0,
    @SerialName("prompt_loss_weight")
    val promptLossWeight: Double = 0.0
)