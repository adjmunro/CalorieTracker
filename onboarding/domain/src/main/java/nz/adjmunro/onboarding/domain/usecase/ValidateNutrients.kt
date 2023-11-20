package nz.adjmunro.onboarding.domain.usecase

import nz.adjmunro.core.domain.preferences.CarbRatio
import nz.adjmunro.core.domain.preferences.FatRatio
import nz.adjmunro.core.domain.preferences.ProteinRatio
import nz.adjmunro.core.util.CoreString
import nz.adjmunro.core.util.UiText
import nz.adjmunro.onboarding.domain.models.CarbInput
import nz.adjmunro.onboarding.domain.models.FatInput
import nz.adjmunro.onboarding.domain.models.ProteinInput

class ValidateNutrients {
    operator fun invoke(
        carbRatioInput: CarbInput,
        proteinRatioInput: ProteinInput,
        fatRatioInput: FatInput,
    ): Result {
        val carbRatio = carbRatioInput.toIntOrNull()
        val proteinRatio = proteinRatioInput.toIntOrNull()
        val fatRatio = fatRatioInput.toIntOrNull()

        if (carbRatio == null || proteinRatio == null || fatRatio == null) {
            return Result.Error(UiText.StringResource(CoreString.error_invalid_values))
        }
        if (carbRatio + proteinRatio + fatRatio != 100) {
            return Result.Error(UiText.StringResource(CoreString.error_not_100_percent))
        }

        return Result.Success(
            carbRatio = carbRatio.toFloat() / 100f,
            proteinRatio = proteinRatio.toFloat() / 100f,
            fatRatio = fatRatio.toFloat() / 100f,
        )
    }

    sealed class Result {
        data class Success(
            val carbRatio: CarbRatio,
            val proteinRatio: ProteinRatio,
            val fatRatio: FatRatio,
        ) : Result()

        data class Error(val message: UiText) : Result()
    }
}
