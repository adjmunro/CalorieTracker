package nz.adjmunro.onboarding.presentation.goal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import nz.adjmunro.core.domain.models.GoalType
import nz.adjmunro.core.domain.preferences.Preferences
import nz.adjmunro.core.util.UiEvent
import javax.inject.Inject

@HiltViewModel
class GoalTypeViewModel @Inject constructor(
    private val preferences: Preferences,
) : ViewModel() {

    var selectedGoalType: GoalType by mutableStateOf(GoalType.KeepWeight)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onGoalTypeClicked(gender: GoalType) {
        selectedGoalType = gender
    }

    fun onNextClicked() {
        viewModelScope.launch {
            preferences.saveGoalType(selectedGoalType)
            _uiEvent.send(UiEvent.Success)
        }
    }
}
