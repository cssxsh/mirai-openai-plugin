package xyz.cssxsh.openai.finetune

import kotlinx.serialization.*

/**
 * @param trainingFile [create-training_file](https://beta.openai.com/docs/api-reference/fine-tunes/create#fine-tunes/create-training_file)
 * @param validationFile [create-validation_file](https://beta.openai.com/docs/api-reference/fine-tunes/create#fine-tunes/create-validation_file)
 * @param model [create-model](https://beta.openai.com/docs/api-reference/fine-tunes/create#fine-tunes/create-model)
 * @param nEpochs [create-n_epochs](https://beta.openai.com/docs/api-reference/fine-tunes/create#fine-tunes/create-n_epochs)
 * @param batchSize [create-batch_size](https://beta.openai.com/docs/api-reference/fine-tunes/create#fine-tunes/create-batch_size)
 * @param learningRateMultiplier [create-learning_rate_multiplier](https://beta.openai.com/docs/api-reference/fine-tunes/create#fine-tunes/create-learning_rate_multiplier)
 * @param promptLossWeight [create-prompt_loss_weight](https://beta.openai.com/docs/api-reference/fine-tunes/create#fine-tunes/create-prompt_loss_weight)
 * @param computeClassificationMetrics [create-compute_classification_metrics](https://beta.openai.com/docs/api-reference/fine-tunes/create#fine-tunes/create-compute_classification_metrics)
 * @param classificationNClasses [create-classification_n_classes](https://beta.openai.com/docs/api-reference/fine-tunes/create#fine-tunes/create-classification_n_classes)
 * @param classificationPositiveClass [create-classification_positive_class](https://beta.openai.com/docs/api-reference/fine-tunes/create#fine-tunes/create-classification_positive_class)
 * @param classificationBetas [create-classification_betas](https://beta.openai.com/docs/api-reference/fine-tunes/create#fine-tunes/create-classification_betas)
 * @param suffix [create-suffix](https://beta.openai.com/docs/api-reference/fine-tunes/create#fine-tunes/create-suffix)
 */
@Serializable
public data class FineTuneRequest(
    @SerialName("training_file")
    val trainingFile: String,
    @SerialName("validation_file")
    val validationFile: String = "",
    @SerialName("model")
    val model: String = "curie",
    @SerialName("n_epochs")
    val nEpochs: Int = 4,
    @SerialName("batch_size")
    val batchSize: Int? = null,
    @SerialName("learning_rate_multiplier")
    val learningRateMultiplier: Double? = null,
    @SerialName("prompt_loss_weight")
    val promptLossWeight: Double = 0.01,
    @SerialName("compute_classification_metrics")
    val computeClassificationMetrics: Boolean = false,
    @SerialName("classification_n_classes")
    val classificationNClasses: Int? = null,
    @SerialName("classification_positive_class")
    val classificationPositiveClass: String? = null,
    @SerialName("classification_betas")
    val classificationBetas: String? = null,
    @SerialName("suffix")
    val suffix: String? = null
)