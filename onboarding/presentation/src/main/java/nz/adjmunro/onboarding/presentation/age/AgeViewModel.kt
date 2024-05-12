package nz.adjmunro.onboarding.presentation.age

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import nz.adjmunro.core.domain.preferences.Age
import nz.adjmunro.core.domain.preferences.Preferences
import nz.adjmunro.core.domain.usecase.FilterOnlyDigits
import nz.adjmunro.core.util.CoreString
import nz.adjmunro.core.util.UiEvent
import nz.adjmunro.core.util.UiEvent.Success
import nz.adjmunro.core.util.UiText
import javax.inject.Inject

typealias AgeInput = String

@HiltViewModel
class AgeViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOnlyDigits: FilterOnlyDigits,
) : ViewModel() {

    var age: AgeInput by mutableStateOf("20")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAgeChanged(age: AgeInput) {
        if (age.length <= 3) {
            this.age = filterOnlyDigits(input = age)
        }
    }

    fun onNextClicked() {
        viewModelScope.launch {
            val ageNumber: Age = age.toIntOrNull() ?: kotlin.run {
                _uiEvent.send(
                    UiEvent.ShowSnackbar(
                        UiText.StringResource(resId = CoreString.error_age_cant_be_empty)
                    )
                )
                return@launch
            }
            preferences.saveAge(age = ageNumber)
            _uiEvent.send(Success)
        }
    }
}
