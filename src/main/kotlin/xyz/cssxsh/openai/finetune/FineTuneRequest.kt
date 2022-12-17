package xyz.cssxsh.openai.finetune

import kotlinx.serialization.*
import xyz.cssxsh.openai.*

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
) {
    public class Builder(@property:OpenAiDsl public var trainingFile: String) {
        @OpenAiDsl
        public fun trainingFile(value: String): Builder = apply {
            trainingFile = value
        }

        @OpenAiDsl
        public var validationFile: String = ""

        @OpenAiDsl
        public fun validationFile(value: String): Builder = apply {
            validationFile = value
        }

        @OpenAiDsl
        public var model: String = "curie"

        @OpenAiDsl
        public fun model(value: String): Builder = apply {
            model = value
        }

        @OpenAiDsl
        public var nEpochs: Int = 4

        @OpenAiDsl
        public fun nEpochs(value: Int): Builder = apply {
            nEpochs = value
        }

        @OpenAiDsl
        public var batchSize: Int? = null

        @OpenAiDsl
        public fun batchSize(value: Int): Builder = apply {
            batchSize = value
        }

        @OpenAiDsl
        public var learningRateMultiplier: Double? = null

        @OpenAiDsl
        public fun learningRateMultiplier(value: Double): Builder = apply {
            learningRateMultiplier = value
        }

        @OpenAiDsl
        public var promptLossWeight: Double = 0.01

        @OpenAiDsl
        public fun promptLossWeight(value: Double): Builder = apply {
            promptLossWeight = value
        }

        @OpenAiDsl
        public var computeClassificationMetrics: Boolean = false

        @OpenAiDsl
        public fun computeClassificationMetrics(value: Boolean): Builder = apply {
            computeClassificationMetrics = value
        }

        @OpenAiDsl
        public var classificationNClasses: Int? = null

        @OpenAiDsl
        public fun classificationNClasses(value: Int): Builder = apply {
            classificationNClasses = value
        }

        @OpenAiDsl
        public var classificationPositiveClass: String? = null

        @OpenAiDsl
        public fun classificationPositiveClass(value: String): Builder = apply {
            classificationPositiveClass = value
        }

        @OpenAiDsl
        public var classificationBetas: String? = null

        @OpenAiDsl
        public fun classificationBetas(value: String): Builder = apply {
            classificationBetas = value
        }

        @OpenAiDsl
        private var suffix: String? = null

        @OpenAiDsl
        public fun suffix(value: String): Builder = apply {
            suffix = value
        }

        public fun build(): FineTuneRequest {
            return FineTuneRequest(
                trainingFile = trainingFile,
                validationFile = validationFile,
                model = model,
                nEpochs = nEpochs,
                batchSize = batchSize,
                learningRateMultiplier = learningRateMultiplier,
                promptLossWeight = promptLossWeight,
                computeClassificationMetrics = computeClassificationMetrics,
                classificationNClasses = classificationNClasses,
                classificationPositiveClass = classificationPositiveClass,
                classificationBetas = classificationBetas,
                suffix = suffix
            )
        }
    }
}