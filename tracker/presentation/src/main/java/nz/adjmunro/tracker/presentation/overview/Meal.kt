package nz.adjmunro.tracker.presentation.overview

import androidx.annotation.DrawableRes
import nz.adjmunro.core.util.CoreDrawable
import nz.adjmunro.core.util.CoreString
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
                name = UiText.StringResource(CoreString.breakfast),
                drawableRes = CoreDrawable.ic_breakfast,
                mealType = MealType.Breakfast,
            ),
            Meal(
                name = UiText.StringResource(CoreString.lunch),
                drawableRes = CoreDrawable.ic_lunch,
                mealType = MealType.Lunch,
            ),
            Meal(
                name = UiText.StringResource(CoreString.dinner),
                drawableRes = CoreDrawable.ic_dinner,
                mealType = MealType.Dinner,
            ),
            Meal(
                name = UiText.StringResource(CoreString.snacks),
                drawableRes = CoreDrawable.ic_snack,
                mealType = MealType.Snack,
            ),
        )
    }
}
