package nz.adjmunro.tracker.data.mapper

import nz.adjmunro.tracker.data.local.entity.TrackedFoodEntity
import nz.adjmunro.tracker.domain.model.MealType
import nz.adjmunro.tracker.domain.model.TrackedFood
import java.time.LocalDate

fun TrackedFoodEntity.toTrackedFood(): TrackedFood {
    return TrackedFood(
        name = name,
        carbs = carbs,
        protein = protein,
        fat = fat,
        imageUrl = imageUrl,
        mealType = MealType.fromString(type),
        amount = amount,
        date = LocalDate.of(year, month, dayOfMonth),
        calories = calories,
        id = id,
    )
}

fun TrackedFood.toTrackedFoodEntity(): TrackedFoodEntity {
    return TrackedFoodEntity(
        name = name,
        carbs = carbs,
        protein = protein,
        fat = fat,
        imageUrl = imageUrl,
        type = mealType.id,
        amount = amount,
        year = date.year,
        month = date.monthValue,
        dayOfMonth = date.dayOfMonth,
        calories = calories,
        id = id,
    )
}
