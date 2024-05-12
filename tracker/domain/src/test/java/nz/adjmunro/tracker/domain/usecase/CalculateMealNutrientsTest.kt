package nz.adjmunro.tracker.domain.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import nz.adjmunro.core.domain.models.ActivityLevel
import nz.adjmunro.core.domain.models.Gender
import nz.adjmunro.core.domain.models.GoalType
import nz.adjmunro.core.domain.models.UserInfo
import nz.adjmunro.core.domain.preferences.Preferences
import nz.adjmunro.tracker.domain.model.MealType
import nz.adjmunro.tracker.domain.model.TrackedFood
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import kotlin.random.Random

class CalculateMealNutrientsTest {

    private lateinit var calculateMealNutrients: CalculateMealNutrients

    @Before
    fun setUp() {
        val preferences = mockk<Preferences>(relaxed = true)
        every { preferences.loadUserInfo() } returns UserInfo(
            gender = Gender.Male,
            age = 30,
            weight = 80f,
            height = 180,
            activityLevel = ActivityLevel.MediumActivity,
            goalType = GoalType.KeepWeight,
            carbRatio = .4f,
            proteinRatio = .3f,
            fatRatio = .3f
        )

        calculateMealNutrients = CalculateMealNutrients(preferences)
    }

    @Test
    fun `calories for breakfast is properly calculated`() {
        val trackedFoods = (0..30).map {
            TrackedFood(
                name = "Food $it",
                calories = Random.nextInt(100),
                carbs = Random.nextInt(100),
                protein = Random.nextInt(100),
                fat = Random.nextInt(100),
                mealType = MealType.entries.random(),
                imageUrl = null,
                amount = 100,
                date = LocalDate.now(),
            )
        }

        val result = calculateMealNutrients(trackedFoods)

        val breakfastCalories =
            result.mealNutrients.values.filter { it.mealType == MealType.Breakfast }
                .sumOf { it.calories }

        val expectedBreakfastCalories =
            trackedFoods.filter { it.mealType == MealType.Breakfast }.sumOf { it.calories }

        assertThat(breakfastCalories).isEqualTo(expectedBreakfastCalories)
    }

    @Test
    fun `carbs for dinner is properly calculated`() {
        val trackedFoods = (0..30).map {
            TrackedFood(
                name = "Food $it",
                calories = Random.nextInt(100),
                carbs = Random.nextInt(100),
                protein = Random.nextInt(100),
                fat = Random.nextInt(100),
                mealType = MealType.entries.random(),
                imageUrl = null,
                amount = 100,
                date = LocalDate.now(),
            )
        }

        val result = calculateMealNutrients(trackedFoods)

        val dinnerCalories =
            result.mealNutrients.values.filter { it.mealType == MealType.Dinner }.sumOf { it.carbs }

        val expectedDinnerCalories =
            trackedFoods.filter { it.mealType == MealType.Dinner }.sumOf { it.carbs }

        assertThat(dinnerCalories).isEqualTo(expectedDinnerCalories)
    }
}
