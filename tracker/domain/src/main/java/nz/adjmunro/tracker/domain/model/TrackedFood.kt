package nz.adjmunro.tracker.domain.model

import java.time.LocalDate

data class TrackedFood(
    val name: String,
    val carbs: Int,
    val protein: Int,
    val fat: Int,
    val imageUrl: String?,
    val type: MealType,
    val amount: Int,
    val date: LocalDate,
    val calories: Int,
    val id: Int? = null,
)
