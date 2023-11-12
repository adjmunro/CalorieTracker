package nz.adjmunro.onboarding.presentation.height

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import nz.adjmunro.core.R
import nz.adjmunro.core.domain.preferences.Height
import nz.adjmunro.core.domain.preferences.Preferences
import nz.adjmunro.core.domain.usecase.FilterOnlyDigits
import nz.adjmunro.core.navigation.Routes
import nz.adjmunro.core.util.UiEvent
import nz.adjmunro.core.util.UiEvent.Navigate
import nz.adjmunro.core.util.UiText
import javax.inject.Inject

typealias HeightInput = String

@HiltViewModel
class HeightViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOnlyDigits: FilterOnlyDigits,
) : ViewModel() {

    var height: HeightInput by mutableStateOf("170")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onHeightChanged(height: HeightInput) {
        if (height.length <= 3) {
            this.height = filterOnlyDigits(input = height)
        }
    }

    fun onNextClicked() {
        viewModelScope.launch {
            val heightNumber: Height = height.toIntOrNull() ?: kotlin.run {
                _uiEvent.send(
                    UiEvent.ShowSnackbar(
                        UiText.StringResource(resId = R.string.error_height_cant_be_empty)
                    )
                )
                return@launch
            }
            preferences.saveHeight(height = heightNumber)
            _uiEvent.send(Navigate(Routes.WEIGHT))
        }
    }
}
