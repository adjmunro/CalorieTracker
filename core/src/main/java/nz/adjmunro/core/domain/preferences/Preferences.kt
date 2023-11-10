package nz.adjmunro.core.domain.preferences

import nz.adjmunro.core.domain.models.ActivityLevel
import nz.adjmunro.core.domain.models.Gender
import nz.adjmunro.core.domain.models.GoalType
import nz.adjmunro.core.domain.models.UserInfo

typealias PreferenceKey = String
typealias Age = Int
typealias Height = Int
typealias Weight = Int
typealias CarbRatio = Float
typealias ProteinRatio = Float
typealias FatRatio = Float

interface Preferences {
    fun saveGender(gender: Gender)
    fun saveAge(age: Age)
    fun saveHeight(height: Height)
    fun saveWeight(weight: Weight)
    fun saveActivityLevel(activityLevel: ActivityLevel)
    fun saveGoalType(goalType: GoalType)
    fun saveCarbRatio(ratio: CarbRatio)
    fun saveProteinRatio(ratio: ProteinRatio)
    fun saveFatRatio(ratio: FatRatio)
    fun loadUserInfo(): UserInfo

    companion object {
        const val KEY_GENDER: PreferenceKey = "gender"
        const val KEY_AGE: PreferenceKey = "age"
        const val KEY_HEIGHT: PreferenceKey = "height"
        const val KEY_WEIGHT: PreferenceKey = "weight"
        const val KEY_ACTIVITY_LEVEL: PreferenceKey = "activity_level"
        const val KEY_GOAL_TYPE: PreferenceKey = "goal_type"
        const val KEY_CARB_RATIO: PreferenceKey = "carb_ratio"
        const val KEY_PROTEIN_RATIO: PreferenceKey = "protein_ratio"
        const val KEY_FAT_RATIO: PreferenceKey = "fat_ratio"
    }
}
