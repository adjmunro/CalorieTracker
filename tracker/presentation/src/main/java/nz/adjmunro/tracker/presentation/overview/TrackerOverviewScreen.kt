package nz.adjmunro.tracker.presentation.overview

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import nz.adjmunro.core.util.UiEvent.Navigate
import nz.adjmunro.coreui.LocalSpacing
import nz.adjmunro.tracker.presentation.components.NutrientsHeader
import nz.adjmunro.tracker.presentation.overview.components.DaySelector
import nz.adjmunro.tracker.presentation.overview.components.ExpandableMeal

@Composable
fun TrackerOverviewScreen(
    viewModel: TrackerOverviewViewModel = hiltViewModel(),
    onNavigate: (Navigate) -> Unit,
) {

    TrackerOverviewScreen(
        state = viewModel.state,
        onEvent = viewModel::onEvent,
        onNavigate = onNavigate,
    )
}

@Composable
fun TrackerOverviewScreen(
    state: TrackerOverviewState = TrackerOverviewState(),
    onEvent: (TrackerOverviewEvent) -> Unit = {},
    onNavigate: (Navigate) -> Unit = {},
) = LazyColumn(
    modifier = Modifier
        .fillMaxSize()
        .padding(bottom = LocalSpacing.current.medium_16),
) {
    item {
        NutrientsHeader(state = state)

        Spacer(modifier = Modifier.padding(LocalSpacing.current.medium_16))

        DaySelector(
            date = state.date,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LocalSpacing.current.medium_16),
            onPreviousDayClicked = { onEvent(TrackerOverviewEvent.OnPreviousDayClicked) },
            onNextDayClicked = { onEvent(TrackerOverviewEvent.OnNextDayClicked) },
        )

        Spacer(modifier = Modifier.padding(LocalSpacing.current.medium_16))
    }

    items(state.meals) { meal ->
        ExpandableMeal(
            meal = meal,
            modifier = Modifier.fillMaxWidth(),
            onToggleClicked = { onEvent(TrackerOverviewEvent.OnToggleMealClicked(meal)) },
        ) {

        }
    }
}
