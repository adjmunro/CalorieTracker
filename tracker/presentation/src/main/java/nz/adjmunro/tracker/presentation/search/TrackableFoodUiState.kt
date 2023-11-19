package nz.adjmunro.tracker.presentation.search

import nz.adjmunro.tracker.domain.model.TrackableFood

data class TrackableFoodUiState(
    val food: TrackableFood,
    val isExpanded: Boolean = false,
    val amount: String = "",
)
