package nz.adjmunro.tracker.presentation.search

import nz.adjmunro.tracker.domain.model.MealType
import nz.adjmunro.tracker.domain.model.TrackableFood
import java.time.LocalDate

sealed class SearchEvent {
    data class OnQueryChanged(val query: String) : SearchEvent()
    data object OnSearch : SearchEvent()
    data class OnToggleTrackableFood(val food: TrackableFood) : SearchEvent()
    data class OnAmountForFoodChange(
        val food: TrackableFood,
        val amount: String,
    ) : SearchEvent()

    data class OnTrackFoodClicked(
        val food: TrackableFood,
        val mealType: MealType,
        val date: LocalDate,
    ) : SearchEvent()

    data class OnSearchFocusChange(val isFocused: Boolean) : SearchEvent()
}
