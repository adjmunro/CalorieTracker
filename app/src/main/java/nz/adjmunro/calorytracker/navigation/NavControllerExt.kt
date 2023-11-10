package nz.adjmunro.calorytracker.navigation

import androidx.navigation.NavController
import nz.adjmunro.core.util.UiEvent

fun NavController.navigate(event: UiEvent.Navigate) {
    this.navigate(event.route)
}
