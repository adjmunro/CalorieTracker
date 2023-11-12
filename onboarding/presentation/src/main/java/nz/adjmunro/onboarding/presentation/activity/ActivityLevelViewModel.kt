package nz.adjmunro.onboarding.presentation.activity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import nz.adjmunro.core.domain.models.ActivityLevel
import nz.adjmunro.core.domain.preferences.Preferences
import nz.adjmunro.core.navigation.Routes
import nz.adjmunro.core.util.UiEvent
import javax.inject.Inject

@HiltViewModel
class ActivityLevelViewModel @Inject constructor(
    private val preferences: Preferences,
) : ViewModel() {

    var selectedActivityLevel: ActivityLevel by mutableStateOf(ActivityLevel.MediumActivity)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onActivityLevelClicked(gender: ActivityLevel) {
        selectedActivityLevel = gender
    }

    fun onNextClicked() {
        viewModelScope.launch {
            preferences.saveActivityLevel(selectedActivityLevel)
            _uiEvent.send(UiEvent.Navigate(Routes.GOAL))
        }
    }
}
