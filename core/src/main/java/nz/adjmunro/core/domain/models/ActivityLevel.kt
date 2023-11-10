package nz.adjmunro.core.domain.models

typealias ActivityLevelPreferenceValue = String

sealed class ActivityLevel(val prefValue: ActivityLevelPreferenceValue) {
    data object LowActivity : ActivityLevel(prefValue = "low_activity")
    data object MediumActivity : ActivityLevel(prefValue = "medium_activity")
    data object HighActivity : ActivityLevel(prefValue = "high_activity")

    companion object {
        fun fromString(prefValue: ActivityLevelPreferenceValue): ActivityLevel = when (prefValue) {
            LowActivity.prefValue -> LowActivity
            MediumActivity.prefValue -> MediumActivity
            HighActivity.prefValue -> HighActivity
            else -> throw IllegalArgumentException(
                "Could not parse ActivityLevelPreferenceValue: $prefValue"
            )
        }
    }
}
