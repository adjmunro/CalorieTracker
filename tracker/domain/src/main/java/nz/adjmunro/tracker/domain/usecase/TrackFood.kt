package nz.adjmunro.tracker.domain.usecase

import nz.adjmunro.tracker.domain.model.MealType
import nz.adjmunro.tracker.domain.model.TrackableFood
import nz.adjmunro.tracker.domain.model.TrackedFood
import nz.adjmunro.tracker.domain.repository.TrackerRepository
import java.time.LocalDate
import kotlin.math.roundToInt

class TrackFood(
    private val repository: TrackerRepository,
) {
    suspend operator fun invoke(
        food: TrackableFood,
        amount: Int,
        mealType: MealType,
        date: LocalDate,
    ) {
        repository.insertTrackedFood(
            food = TrackedFood(
                name = food.name,
                carbs = (amount * food.carbsPer100g / 100f).roundToInt(),
                protein = (amount * food.proteinPer100g / 100f).roundToInt(),
                fat = (amount * food.fatPer100g / 100f).roundToInt(),
                calories = (amount * food.caloriesPer100g / 100f).roundToInt(),
                imageUrl = food.imageUrl,
                mealType = mealType,
                amount = amount,
                date = date,
            )
        )
    }
}
