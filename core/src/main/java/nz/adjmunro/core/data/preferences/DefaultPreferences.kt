package nz.adjmunro.core.data.preferences

import android.content.SharedPreferences
import nz.adjmunro.core.domain.models.ActivityLevel
import nz.adjmunro.core.domain.models.Gender
import nz.adjmunro.core.domain.models.Gender.Male
import nz.adjmunro.core.domain.models.GoalType
import nz.adjmunro.core.domain.models.UserInfo
import nz.adjmunro.core.domain.preferences.Age
import nz.adjmunro.core.domain.preferences.CarbRatio
import nz.adjmunro.core.domain.preferences.FatRatio
import nz.adjmunro.core.domain.preferences.Height
import nz.adjmunro.core.domain.preferences.Preferences
import nz.adjmunro.core.domain.preferences.ProteinRatio
import nz.adjmunro.core.domain.preferences.Weight

class DefaultPreferences(
    private val sharedPref: SharedPreferences,
) : Preferences {
    override fun saveGender(gender: Gender) = sharedPref.edit()
        .putString(Preferences.KEY_GENDER, gender.prefValue)
        .apply()

    override fun saveAge(age: Age) = sharedPref.edit()
        .putInt(Preferences.KEY_AGE, age)
        .apply()

    override fun saveHeight(height: Height) = sharedPref.edit()
        .putInt(Preferences.KEY_HEIGHT, height)
        .apply()

    override fun saveWeight(weight: Weight) = sharedPref.edit()
        .putFloat(Preferences.KEY_WEIGHT, weight)
        .apply()

    override fun saveActivityLevel(activityLevel: ActivityLevel) = sharedPref.edit()
        .putString(Preferences.KEY_ACTIVITY_LEVEL, activityLevel.prefValue)
        .apply()

    override fun saveGoalType(goalType: GoalType) = sharedPref.edit()
        .putString(Preferences.KEY_GOAL_TYPE, goalType.prefValue)
        .apply()

    override fun saveCarbRatio(ratio: CarbRatio) = sharedPref.edit()
        .putFloat(Preferences.KEY_CARB_RATIO, ratio)
        .apply()

    override fun saveProteinRatio(ratio: ProteinRatio) = sharedPref.edit()
        .putFloat(Preferences.KEY_PROTEIN_RATIO, ratio)
        .apply()

    override fun saveFatRatio(ratio: FatRatio) = sharedPref.edit()
        .putFloat(Preferences.KEY_FAT_RATIO, ratio)
        .apply()

    override fun loadUserInfo(): UserInfo {
        val gender = sharedPref.getString(Preferences.KEY_GENDER, null)
        val age = sharedPref.getInt(Preferences.KEY_AGE, -1)
        val height = sharedPref.getInt(Preferences.KEY_HEIGHT, -1)
        val weight = sharedPref.getFloat(Preferences.KEY_WEIGHT, -1f)
        val activityLevel = sharedPref.getString(Preferences.KEY_ACTIVITY_LEVEL, null)
        val goalType = sharedPref.getString(Preferences.KEY_GOAL_TYPE, null)
        val carbRatio = sharedPref.getFloat(Preferences.KEY_CARB_RATIO, -1f)
        val proteinRatio = sharedPref.getFloat(Preferences.KEY_PROTEIN_RATIO, -1f)
        val fatRatio = sharedPref.getFloat(Preferences.KEY_FAT_RATIO, -1f)

        return UserInfo(
            gender = Gender.fromString(
                prefValue = gender ?: Male.prefValue,
            ),
            age = age,
            height = height,
            weight = weight,
            activityLevel = ActivityLevel.fromString(
                prefValue = activityLevel ?: ActivityLevel.MediumActivity.prefValue,
            ),
            goalType = GoalType.fromString(
                prefValue = goalType ?: GoalType.KeepWeight.prefValue,
            ),
            carbRatio = carbRatio,
            proteinRatio = proteinRatio,
            fatRatio = fatRatio,
        )
    }
}
