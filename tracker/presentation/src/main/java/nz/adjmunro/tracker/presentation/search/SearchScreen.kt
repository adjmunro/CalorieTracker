package nz.adjmunro.tracker.presentation.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import nz.adjmunro.core.util.CoreString
import nz.adjmunro.core.util.UiEvent.NavigateUp
import nz.adjmunro.core.util.UiEvent.ShowSnackbar
import nz.adjmunro.coreui.LocalSpacing
import nz.adjmunro.tracker.domain.model.MealType
import nz.adjmunro.tracker.presentation.search.SearchEvent.OnToggleTrackableFood
import nz.adjmunro.tracker.presentation.search.SearchEvent.OnTrackFoodClicked
import nz.adjmunro.tracker.presentation.search.components.SearchTextField
import nz.adjmunro.tracker.presentation.search.components.TrackableFoodItem
import java.time.LocalDate

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    mealName: String,
    dayOfMonth: Int,
    month: Int,
    year: Int,
    scaffoldState: ScaffoldState,
    onNavigateUp: () -> Unit,
) {
    val state = viewModel.state
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = keyboardController) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message.asString(context = context)
                    )
                    keyboardController?.hide()
                }

                is NavigateUp -> onNavigateUp()
                else -> Unit
            }
        }
    }

    SearchScreen(
        state = viewModel.state,
        mealName = mealName,
        dayOfMonth = dayOfMonth,
        month = month,
        year = year,
        onEvent = viewModel::onEvent,
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SearchScreen(
    state: SearchState = SearchState(),
    mealName: String,
    dayOfMonth: Int,
    month: Int,
    year: Int,
    onEvent: (SearchEvent) -> Unit = {},
) = Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(LocalSpacing.current.medium_16),
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Text(
        text = stringResource(
            id = CoreString.add_meal,
            mealName
        ),
        style = MaterialTheme.typography.h2,
    )
    Spacer(modifier = Modifier.height(LocalSpacing.current.medium_16))
    SearchTextField(
        text = state.query,
        shouldShowHint = state.isHintVisible,
        onSearch = {
            keyboardController?.hide()
            onEvent(SearchEvent.OnSearch)
        },
        onValueChanged = { onEvent(SearchEvent.OnQueryChanged(it)) },
        onFocusChanged = { focusState ->
            onEvent(SearchEvent.OnSearchFocusChange(focusState.isFocused))
        },
    )
    Spacer(modifier = Modifier.height(LocalSpacing.current.medium_16))
    LazyColumn(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
        items(state.trackableFoodList) { food ->
            TrackableFoodItem(
                trackableFoodUiState = food,
                modifier = Modifier.fillMaxWidth(),
                onAmountChanged = { amount ->
                    onEvent(
                        SearchEvent.OnAmountForFoodChange(
                            food.food,
                            amount
                        )
                    )
                },
                onTrack = {
                    keyboardController?.hide()
                    onEvent(
                        OnTrackFoodClicked(
                            food = food.food,
                            mealType = MealType.fromString(mealName),
                            date = LocalDate.of(
                                year,
                                month,
                                dayOfMonth
                            ),
                        )
                    )
                },
                onClick = {
                    onEvent(OnToggleTrackableFood(food.food))
                },
            )
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        when {
            state.isSearching -> CircularProgressIndicator()
            state.trackableFoodList.isEmpty() -> {
                Text(
                    text = stringResource(id = CoreString.no_results),
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}
