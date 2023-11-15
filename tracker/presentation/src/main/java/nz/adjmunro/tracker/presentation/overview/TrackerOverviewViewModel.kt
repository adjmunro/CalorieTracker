package nz.adjmunro.tracker.presentation.overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import nz.adjmunro.core.domain.preferences.Preferences
import nz.adjmunro.core.navigation.Routes
import nz.adjmunro.core.util.UiEvent
import nz.adjmunro.core.util.UiEvent.Navigate
import nz.adjmunro.tracker.domain.usecase.TrackerUseCases
import nz.adjmunro.tracker.presentation.overview.TrackerOverviewEvent.OnAddFoodClicked
import nz.adjmunro.tracker.presentation.overview.TrackerOverviewEvent.OnDeleteTrackedFoodClicked
import nz.adjmunro.tracker.presentation.overview.TrackerOverviewEvent.OnNextDayClicked
import nz.adjmunro.tracker.presentation.overview.TrackerOverviewEvent.OnPreviousDayClicked
import nz.adjmunro.tracker.presentation.overview.TrackerOverviewEvent.OnToggleMealClicked
import javax.inject.Inject

@HiltViewModel
class TrackerOverviewViewModel @Inject constructor(
    preferences: Preferences,
    private val trackerUseCases: TrackerUseCases,
) : ViewModel() {

    var state by mutableStateOf(TrackerOverviewState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var getFoodsForDateJob: Job? = null

    init {
        preferences.saveShouldShowOnboarding(shouldShowOnboarding = false)
    }

    fun onEvent(event: TrackerOverviewEvent) {
        when (event) {
            is OnAddFoodClicked -> viewModelScope.launch {
                val route = buildString {
                    append(Routes.SEARCH)
                    append("/${event.meal.mealType.id}")
                    append("/${state.date.dayOfMonth}")
                    append("/${state.date.monthValue}")
                    append("/${state.date.year}")
                }
                _uiEvent.send(Navigate(route = route))
            }

            is OnDeleteTrackedFoodClicked -> viewModelScope.launch {
                trackerUseCases.deleteTrackedFood(event.trackedFood)
                refreshFoods()
            }

            is OnNextDayClicked -> {
                state = state.copy(date = state.date.plusDays(1))
                refreshFoods()
            }

            is OnPreviousDayClicked -> {
                state = state.copy(date = state.date.minusDays(1))
                refreshFoods()
            }

            is OnToggleMealClicked -> {
                state = state.copy(meals = state.meals.map {
                    if (it.name != event.meal.name) it
                    else it.copy(isExpanded = !it.isExpanded)
                })
            }
        }
    }

    private fun refreshFoods() {
        getFoodsForDateJob?.cancel()
        getFoodsForDateJob = trackerUseCases.getFoodsForDate(date = state.date)
            .onEach { foods ->
                val nutrientsResult = trackerUseCases.calculateMealNutrients(foods)
                state = state.copy(
                    trackedFoods = foods,
                    totalCarbs = nutrientsResult.totalCarbs,
                    totalProtein = nutrientsResult.totalProtein,
                    totalFat = nutrientsResult.totalFat,
                    totalCalories = nutrientsResult.totalCalories,
                    carbsGoal = nutrientsResult.carbsGoal,
                    proteinGoal = nutrientsResult.proteinGoal,
                    fatGoal = nutrientsResult.fatGoal,
                    caloriesGoal = nutrientsResult.caloriesGoal,
                    meals = state.meals.map { meal: Meal ->
                        val nutrientsForMeal =
                            nutrientsResult.mealNutrients[meal.mealType] ?: return@map meal.copy(
                                carbs = 0,
                                protein = 0,
                                fat = 0,
                                calories = 0,
                            )
                        meal.copy(
                            carbs = nutrientsForMeal.carbs,
                            protein = nutrientsForMeal.protein,
                            fat = nutrientsForMeal.fat,
                            calories = nutrientsForMeal.calories,
                        )
                    },
                )
            }
            .launchIn(viewModelScope)
    }

}
