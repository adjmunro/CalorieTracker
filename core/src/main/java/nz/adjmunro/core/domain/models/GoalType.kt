package nz.adjmunro.core.domain.models

typealias GoalTypePreferenceValue = String

sealed class GoalType(val prefValue: GoalTypePreferenceValue) {
    data object LoseWeight : GoalType(prefValue = "lose_weight")
    data object KeepWeight : GoalType(prefValue = "keep_weight")
    data object GainWeight : GoalType(prefValue = "gain_weight")

    companion object {
        fun fromString(prefValue: GoalTypePreferenceValue): GoalType = when (prefValue) {
            LoseWeight.prefValue -> LoseWeight
            KeepWeight.prefValue -> KeepWeight
            GainWeight.prefValue -> GainWeight
            else -> throw IllegalArgumentException(
                "Could not parse GoalTypePreferenceValue: $prefValue"
            )
        }
    }
}
