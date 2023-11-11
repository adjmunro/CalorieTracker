package nz.adjmunro.onboarding.presentation.gender

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import nz.adjmunro.core.domain.models.Gender
import nz.adjmunro.core.domain.preferences.Preferences
import nz.adjmunro.core.navigation.Routes
import nz.adjmunro.core.util.UiEvent
import javax.inject.Inject

@HiltViewModel
class GenderViewModel @Inject constructor(
    private val preferences: Preferences,
) : ViewModel() {

    var selectedGender by mutableStateOf<Gender>(Gender.Male)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onGenderClicked(gender: Gender) {
        selectedGender = gender
    }

    fun onNextClicked() {
        viewModelScope.launch {
            preferences.saveGender(selectedGender)
            _uiEvent.send(UiEvent.Navigate(Routes.AGE))
        }
    }
}
