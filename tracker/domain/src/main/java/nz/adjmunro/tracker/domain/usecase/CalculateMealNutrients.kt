package nz.adjmunro.tracker.domain.usecase

import nz.adjmunro.core.domain.models.ActivityLevel
import nz.adjmunro.core.domain.models.Gender
import nz.adjmunro.core.domain.models.GoalType
import nz.adjmunro.core.domain.models.UserInfo
import nz.adjmunro.core.domain.preferences.Preferences
import nz.adjmunro.tracker.domain.model.MealType
import nz.adjmunro.tracker.domain.model.TrackedFood
import kotlin.math.roundToInt

class CalculateMealNutrients(
    private val preferences: Preferences,
) {
    operator fun invoke(trackedFoods: List<TrackedFood>): Result {
        val allNutrients = trackedFoods.groupBy { it.mealType }.mapValues { (mealType, foods) ->
            MealNutrients(
                carbs = foods.sumOf { it.carbs },
                protein = foods.sumOf { it.protein },
                fat = foods.sumOf { it.fat },
                calories = foods.sumOf { it.calories },
                mealType = mealType,
            )
        }

        val totalCarbs = allNutrients.values.sumOf { it.carbs }
        val totalProtein = allNutrients.values.sumOf { it.protein }
        val totalFat = allNutrients.values.sumOf { it.fat }
        val totalCalories = allNutrients.values.sumOf { it.calories }

        val userInfo = preferences.loadUserInfo()
        val calorieGoal = dailyCalorieRequirement(userInfo)
        val carbsGoal = calcGoal(calorieGoal, userInfo.carbRatio, KILOCALORIES_PER_GRAM_OF_CARBS)
        val proteinGoal =
            calcGoal(calorieGoal, userInfo.proteinRatio, KILOCALORIES_PER_GRAM_OF_PROTEIN)
        val fatGoal = calcGoal(calorieGoal, userInfo.fatRatio, KILOCALORIES_PER_GRAM_OF_FAT)

        return Result(
            carbsGoal = carbsGoal,
            proteinGoal = proteinGoal,
            fatGoal = fatGoal,
            caloriesGoal = calorieGoal,
            totalCarbs = totalCarbs,
            totalProtein = totalProtein,
            totalFat = totalFat,
            totalCalories = totalCalories,
            mealNutrients = allNutrients,
        )
    }

    private fun calcGoal(calorieGoal: Int, ratio: Float, kCalPerGram: Float): Int =
        (calorieGoal * ratio / kCalPerGram).roundToInt()

    /**
     * Basal Metabolic Rate
     * Calculates how many calories a person burns on any given day if they don't do any exercise.
     */
    private fun bmr(userInfo: UserInfo): Int = when (userInfo.gender) {
        is Gender.Male -> {
            66.47 + 13.75 * userInfo.weight + 5f * userInfo.height - 6.75 * userInfo.age
        }

        is Gender.Female -> {
            665.09 + 9.56 * userInfo.weight + 1.84 * userInfo.height - 4.67 * userInfo.age
        }
    }.roundToInt()

    /**
     * Daily Calorie Requirement
     * Calculates how many calories a person burns on any given day based on their [ActivityLevel] and [GoalType].
     */
    private fun dailyCalorieRequirement(userInfo: UserInfo): Int {
        val activityFactor = when (userInfo.activityLevel) {
            is ActivityLevel.LowActivity -> 1.2f
            is ActivityLevel.MediumActivity -> 1.3f
            is ActivityLevel.HighActivity -> 1.4f
        }
        // Calorie extra is an offset to DCR based on the goal type.
        val calorieExtra = when (userInfo.goalType) {
            is GoalType.LoseWeight -> -500
            is GoalType.KeepWeight -> 0
            is GoalType.GainWeight -> 500
        }
        return (bmr(userInfo) * activityFactor + calorieExtra).roundToInt()
    }

    data class MealNutrients(
        val carbs: Int,
        val protein: Int,
        val fat: Int,
        val calories: Int,
        val mealType: MealType,
    )

    data class Result(
        val carbsGoal: Int,
        val proteinGoal: Int,
        val fatGoal: Int,
        val caloriesGoal: Int,
        val totalCarbs: Int,
        val totalProtein: Int,
        val totalFat: Int,
        val totalCalories: Int,
        val mealNutrients: Map<MealType, MealNutrients>,
    )

    companion object {
        private const val KILOCALORIES_PER_GRAM_OF_CARBS = 4f
        private const val KILOCALORIES_PER_GRAM_OF_PROTEIN = 4f
        private const val KILOCALORIES_PER_GRAM_OF_FAT = 9f
    }
}
