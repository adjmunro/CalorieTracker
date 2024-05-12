package nz.adjmunro.onboarding.presentation.weight

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
import nz.adjmunro.core.domain.preferences.Weight
import nz.adjmunro.core.util.CoreString
import nz.adjmunro.core.util.UiEvent
import nz.adjmunro.core.util.UiText
import javax.inject.Inject

typealias WeightInput = String

@HiltViewModel
class WeightViewModel @Inject constructor(
    private val preferences: Preferences,
) : ViewModel() {

    var weight: WeightInput by mutableStateOf("70.0")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onWeightChanged(weight: WeightInput) {
        if (weight.length <= 5) {
            this.weight = weight
        }
    }

    fun onNextClicked() {
        viewModelScope.launch {
            val weightNumber: Weight = weight.toFloatOrNull() ?: kotlin.run {
                _uiEvent.send(
                    UiEvent.ShowSnackbar(
                        UiText.StringResource(resId = CoreString.error_weight_cant_be_empty)
                    )
                )
                return@launch
            }
            preferences.saveWeight(weight = weightNumber)
            _uiEvent.send(UiEvent.Success)
        }
    }
}
