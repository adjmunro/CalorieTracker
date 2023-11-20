package nz.adjmunro.onboarding.presentation.age

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import nz.adjmunro.core.util.CoreString
import nz.adjmunro.core.util.UiEvent.Navigate
import nz.adjmunro.core.util.UiEvent.ShowSnackbar
import nz.adjmunro.coreui.LocalSpacing
import nz.adjmunro.onboarding.presentation.components.ActionButton
import nz.adjmunro.onboarding.presentation.components.UnitTextField

@Composable
fun AgeScreen(
    viewModel: AgeViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState,
    onNavigate: (Navigate) -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is Navigate -> onNavigate(event)
                is ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message.asString(context = context)
                    )
                }

                else -> Unit
            }
        }
    }

    AgeScreen(
        ageInput = viewModel.age,
        onAgeChanged = viewModel::onAgeChanged,
        onNextClicked = viewModel::onNextClicked,
    )
}

@Composable
private fun AgeScreen(
    ageInput: AgeInput,
    onAgeChanged: (AgeInput) -> Unit = {},
    onNextClicked: () -> Unit = {},
) = Box(
    modifier = Modifier
        .fillMaxSize()
        .padding(LocalSpacing.current.large_24)
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = CoreString.whats_your_age),
            style = MaterialTheme.typography.h3,
        )

        Spacer(modifier = Modifier.height(LocalSpacing.current.medium_16))

        UnitTextField(
            value = ageInput,
            onValueChange = onAgeChanged,
            unit = stringResource(id = CoreString.years),
        )
    }

    ActionButton(
        text = stringResource(id = CoreString.next),
        modifier = Modifier.align(Alignment.BottomEnd),
        onClick = onNextClicked,
    )
}
