package nz.adjmunro.tracker.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import nz.adjmunro.core.util.UiEvent.NavigateUp
import nz.adjmunro.core.util.UiEvent.ShowSnackbar
import nz.adjmunro.coreui.LocalSpacing
import nz.adjmunro.tracker.presentation.search.components.SearchTextField

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
    Text(
        text = stringResource(
            id = nz.adjmunro.core.R.string.add_meal,
            mealName
        ),
        style = MaterialTheme.typography.h2,
    )
    Spacer(modifier = Modifier.height(LocalSpacing.current.medium_16))
    SearchTextField(
        text = state.query,
        onSearch = { onEvent(SearchEvent.OnSearch) },
        onValueChanged = { onEvent(SearchEvent.OnQueryChanged(it)) },
        onFocusChanged = { focusState ->
            onEvent(SearchEvent.OnSearchFocusChange(focusState.isFocused))
        },
    )
}
