package nz.adjmunro.onboarding.presentation.nutrient

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import nz.adjmunro.core.domain.preferences.Preferences
import nz.adjmunro.core.domain.usecase.FilterOnlyDigits
import nz.adjmunro.core.util.UiEvent
import nz.adjmunro.onboarding.domain.models.NutrientGoalState
import nz.adjmunro.onboarding.domain.usecase.ValidateNutrients
import javax.inject.Inject

@HiltViewModel
class NutrientGoalViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOnlyDigits: FilterOnlyDigits,
    private val validateNutrients: ValidateNutrients,
) : ViewModel() {

    var nutrients by mutableStateOf(NutrientGoalState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: NutrientGoalEvent) {
        when (event) {
            is NutrientGoalEvent.OnCarbRationChanged -> {
                nutrients = nutrients.copy(
                    carbRatio = filterOnlyDigits(event.ratio),
                )
            }

            is NutrientGoalEvent.OnProteinRatioChanged -> {
                nutrients = nutrients.copy(
                    proteinRatio = filterOnlyDigits(event.ratio),
                )
            }

            is NutrientGoalEvent.OnFatRatioChanged -> {
                nutrients = nutrients.copy(
                    fatRatio = filterOnlyDigits(event.ratio),
                )
            }

            is NutrientGoalEvent.OnNextClicked -> {
                val result = validateNutrients(
                    carbRatioInput = nutrients.carbRatio,
                    proteinRatioInput = nutrients.proteinRatio,
                    fatRatioInput = nutrients.fatRatio,
                )
                when (result) {
                    is ValidateNutrients.Result.Success -> {
                        preferences.saveCarbRatio(result.carbRatio)
                        preferences.saveProteinRatio(result.proteinRatio)
                        preferences.saveFatRatio(result.fatRatio)
                        viewModelScope.launch {
                            _uiEvent.send(UiEvent.Success)
                        }
                    }

                    is ValidateNutrients.Result.Error -> {
                        viewModelScope.launch {
                            _uiEvent.send(UiEvent.ShowSnackbar(result.message))
                        }
                    }
                }
            }
        }
    }

}
