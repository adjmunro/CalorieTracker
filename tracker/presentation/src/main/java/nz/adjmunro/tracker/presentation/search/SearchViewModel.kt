package nz.adjmunro.tracker.presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import nz.adjmunro.core.domain.usecase.FilterOnlyDigits
import nz.adjmunro.core.util.CoreString
import nz.adjmunro.core.util.UiEvent
import nz.adjmunro.core.util.UiText
import nz.adjmunro.tracker.domain.usecase.TrackerUseCases
import nz.adjmunro.tracker.presentation.search.SearchEvent.OnAmountForFoodChange
import nz.adjmunro.tracker.presentation.search.SearchEvent.OnQueryChanged
import nz.adjmunro.tracker.presentation.search.SearchEvent.OnSearch
import nz.adjmunro.tracker.presentation.search.SearchEvent.OnSearchFocusChange
import nz.adjmunro.tracker.presentation.search.SearchEvent.OnToggleTrackableFood
import nz.adjmunro.tracker.presentation.search.SearchEvent.OnTrackFoodClicked
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val trackerUseCases: TrackerUseCases,
    private val filterOnlyDigits: FilterOnlyDigits,
) : ViewModel() {

    var state by mutableStateOf(SearchState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SearchEvent) {
        when (event) {
            is OnAmountForFoodChange -> {
                state = state.copy(
                    trackableFoodList = state.trackableFoodList.map {
                        if (it.food != event.food) it
                        else it.copy(amount = filterOnlyDigits(event.amount))
                    },
                )
            }

            is OnQueryChanged -> {
                state = state.copy(query = event.query)
            }

            is OnSearch -> {
                executeSearch()
            }

            is OnSearchFocusChange -> {
                state = state.copy(isHintVisible = !event.isFocused && state.query.isBlank())
            }

            is OnToggleTrackableFood -> {
                state = state.copy(
                    trackableFoodList = state.trackableFoodList.map {
                        if (it.food != event.food) it
                        else it.copy(isExpanded = !it.isExpanded)
                    },
                )
            }

            is OnTrackFoodClicked -> {
                trackFood(event)
            }
        }
    }

    private fun executeSearch() {
        viewModelScope.launch {
            state = state.copy(
                isSearching = true,
                trackableFoodList = emptyList(),
            )
            trackerUseCases.searchFood(state.query)
                .onSuccess { foods ->
                    state = state.copy(
                        trackableFoodList = foods.map { TrackableFoodUiState(it) },
                        isSearching = false,
                        query = "",
                    )
                }
                .onFailure {
                    state = state.copy(isSearching = false)
                    _uiEvent.send(UiEvent.ShowSnackbar(UiText.of(CoreString.error_something_went_wrong)))
                }
        }
    }

    private fun trackFood(event: OnTrackFoodClicked) {
        viewModelScope.launch {
            val uiState = state.trackableFoodList.firstOrNull { it.food == event.food }

            trackerUseCases.trackFood(
                food = uiState?.food ?: return@launch,
                amount = uiState.amount.toIntOrNull() ?: return@launch,
                mealType = event.mealType,
                date = event.date,
            )
            _uiEvent.send(UiEvent.NavigateUp)
        }
    }
}
