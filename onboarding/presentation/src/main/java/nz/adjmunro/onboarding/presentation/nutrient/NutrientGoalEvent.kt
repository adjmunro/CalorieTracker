package nz.adjmunro.onboarding.presentation.nutrient

import nz.adjmunro.onboarding.domain.models.CarbInput
import nz.adjmunro.onboarding.domain.models.FatInput
import nz.adjmunro.onboarding.domain.models.ProteinInput

sealed class NutrientGoalEvent {
    data class OnCarbRationChanged(val ratio: CarbInput) : NutrientGoalEvent()
    data class OnProteinRatioChanged(val ratio: ProteinInput) : NutrientGoalEvent()
    data class OnFatRatioChanged(val ratio: FatInput) : NutrientGoalEvent()
    data object OnNextClicked : NutrientGoalEvent()
}
