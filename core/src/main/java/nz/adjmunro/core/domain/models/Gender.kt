package nz.adjmunro.core.domain.models

typealias GenderPreferenceValue = String

sealed class Gender(val prefValue: GenderPreferenceValue) {
    data object Male : Gender(prefValue = "male")
    data object Female : Gender(prefValue = "female")

    companion object {
        fun fromString(prefValue: GenderPreferenceValue): Gender = when (prefValue) {
            Male.prefValue -> Male
            Female.prefValue -> Female
            else -> throw IllegalArgumentException(
                "Could not parse GenderPreferenceValue: $prefValue"
            )
        }
    }
}
