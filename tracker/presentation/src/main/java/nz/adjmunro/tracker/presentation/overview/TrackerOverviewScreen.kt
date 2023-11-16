package nz.adjmunro.tracker.presentation.overview

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import nz.adjmunro.core.util.UiEvent.Navigate
import nz.adjmunro.coreui.LocalSpacing
import nz.adjmunro.tracker.presentation.components.NutrientsHeader

@Composable
fun TrackerOverviewScreen(
    viewModel: TrackerOverviewViewModel = hiltViewModel(),
    onNavigate: (Navigate) -> Unit,
) {

    TrackerOverviewScreen(
        state = viewModel.state,
        onNavigate = onNavigate,
    )
}

@Composable
fun TrackerOverviewScreen(
    state: TrackerOverviewState,
    onNavigate: (Navigate) -> Unit,
) = LazyColumn(
    modifier = Modifier
        .fillMaxSize()
        .padding(bottom = LocalSpacing.current.medium_16),
) {
    item {
        NutrientsHeader(state = state)
    }
}
