package nz.adjmunro.core.util

import nz.adjmunro.core.navigation.Route

sealed class UiEvent {
    data class Navigate(val route: Route) : UiEvent()
    data object NavigateUp : UiEvent()
    data class ShowSnackbar(val message: UiText) : UiEvent()
}
