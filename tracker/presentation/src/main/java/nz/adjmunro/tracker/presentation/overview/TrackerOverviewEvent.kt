package nz.adjmunro.tracker.presentation.overview

import nz.adjmunro.tracker.domain.model.TrackedFood

sealed class TrackerOverviewEvent {
    data object OnNextDayClicked : TrackerOverviewEvent()
    data object OnPreviousDayClicked : TrackerOverviewEvent()
    data class OnToggleMealClicked(val meal: Meal) : TrackerOverviewEvent()
    data class OnDeleteTrackedFoodClicked(val trackedFood: TrackedFood) : TrackerOverviewEvent()
    data class OnAddFoodClicked(val meal: Meal) : TrackerOverviewEvent()
}
