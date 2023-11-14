package nz.adjmunro.tracker.domain.model

sealed class MealType(val id: String) {
    data object Breakfast : MealType(id = "breakfast")
    data object Lunch : MealType(id = "lunch")
    data object Dinner : MealType(id = "dinner")
    data object Snack : MealType(id = "snack")
    data object Other : MealType(id = "other")

    companion object {
        fun fromString(name: String): MealType = when (name) {
            Breakfast.id -> Breakfast
            Lunch.id -> Lunch
            Dinner.id -> Dinner
            Snack.id -> Snack
            else -> Other
        }
    }
}
