package nz.adjmunro.onboarding.domain.models

typealias CarbInput = String
typealias FatInput = String
typealias ProteinInput = String

data class NutrientGoalState(
    val carbRatio: CarbInput = "40",
    val proteinRatio: ProteinInput = "30",
    val fatRatio: FatInput = "30",
)
