package nz.adjmunro.onboarding.presentation.nutrient

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
import nz.adjmunro.core.R
import nz.adjmunro.core.util.UiEvent.Navigate
import nz.adjmunro.core.util.UiEvent.ShowSnackbar
import nz.adjmunro.coreui.LocalSpacing
import nz.adjmunro.onboarding.domain.models.NutrientGoalState
import nz.adjmunro.onboarding.presentation.components.ActionButton
import nz.adjmunro.onboarding.presentation.components.UnitTextField

@Composable
fun NutrientGoalScreen(
    viewModel: NutrientGoalViewModel = hiltViewModel(),
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

    NutrientGoalScreen(
        nutrients = viewModel.nutrients,
        onEvent = viewModel::onEvent,
    )
}

@Composable
private fun NutrientGoalScreen(
    nutrients: NutrientGoalState,
    onEvent: (NutrientGoalEvent) -> Unit = {},
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
            text = stringResource(id = R.string.what_are_your_nutrient_goals),
            style = MaterialTheme.typography.h3,
        )

        Spacer(modifier = Modifier.height(LocalSpacing.current.medium_16))

        UnitTextField(
            value = nutrients.carbRatio,
            unit = stringResource(id = R.string.percent_carbs),
            onValueChange = {
                onEvent(NutrientGoalEvent.OnCarbRationChanged(it))
            },
        )

        Spacer(modifier = Modifier.height(LocalSpacing.current.medium_16))

        UnitTextField(
            value = nutrients.proteinRatio,
            unit = stringResource(id = R.string.percent_proteins),
            onValueChange = {
                onEvent(NutrientGoalEvent.OnProteinRatioChanged(it))
            },
        )

        Spacer(modifier = Modifier.height(LocalSpacing.current.medium_16))

        UnitTextField(
            value = nutrients.fatRatio,
            unit = stringResource(id = R.string.percent_fats),
            onValueChange = {
                onEvent(NutrientGoalEvent.OnFatRatioChanged(it))
            },
        )
    }

    ActionButton(
        text = stringResource(id = R.string.next),
        modifier = Modifier.align(Alignment.BottomEnd),
        onClick = { onEvent(NutrientGoalEvent.OnNextClicked) },
    )
}
