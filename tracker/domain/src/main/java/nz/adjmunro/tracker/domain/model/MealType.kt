package nz.adjmunro.tracker.domain.model

enum class MealType(val id: String) {
    Breakfast(id = "breakfast"), Lunch(id = "lunch"), Dinner(id = "dinner"), Snack(id = "snack");

    companion object {
        fun fromString(name: String): MealType = when (name.lowercase()) {
            Breakfast.id -> Breakfast
            Lunch.id -> Lunch
            Dinner.id -> Dinner
            Snack.id -> Snack
            else -> throw IllegalArgumentException("Unknown meal type: $name")
        }
    }
}
