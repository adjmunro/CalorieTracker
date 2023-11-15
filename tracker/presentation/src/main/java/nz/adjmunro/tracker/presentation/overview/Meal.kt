package nz.adjmunro.tracker.presentation.overview

import androidx.annotation.DrawableRes
import nz.adjmunro.core.util.UiText
import nz.adjmunro.tracker.domain.model.MealType

data class Meal(
    val name: UiText,
    @DrawableRes val drawableRes: Int,
    val mealType: MealType,
    val carbs: Int = 0,
    val protein: Int = 0,
    val fat: Int = 0,
    val calories: Int = 0,
    val isExpanded: Boolean = false,
) {
    companion object {
        val default: List<Meal> = listOf(
            Meal(
                name = UiText.StringResource(nz.adjmunro.core.R.string.breakfast),
                drawableRes = nz.adjmunro.core.R.drawable.ic_breakfast,
                mealType = MealType.Breakfast,
            ),
            Meal(
                name = UiText.StringResource(nz.adjmunro.core.R.string.lunch),
                drawableRes = nz.adjmunro.core.R.drawable.ic_lunch,
                mealType = MealType.Lunch,
            ),
            Meal(
                name = UiText.StringResource(nz.adjmunro.core.R.string.dinner),
                drawableRes = nz.adjmunro.core.R.drawable.ic_dinner,
                mealType = MealType.Dinner,
            ),
            Meal(
                name = UiText.StringResource(nz.adjmunro.core.R.string.snacks),
                drawableRes = nz.adjmunro.core.R.drawable.ic_snack,
                mealType = MealType.Snack,
            ),
        )
    }
}
