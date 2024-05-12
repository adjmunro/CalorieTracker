package nz.adjmunro.onboarding.presentation.goal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import nz.adjmunro.core.domain.models.GoalType
import nz.adjmunro.core.util.CoreString
import nz.adjmunro.core.util.UiEvent.Success
import nz.adjmunro.coreui.LocalSpacing
import nz.adjmunro.onboarding.presentation.components.ActionButton
import nz.adjmunro.onboarding.presentation.components.SelectableButton

@Composable
fun GoalTypeScreen(
    viewModel: GoalTypeViewModel = hiltViewModel(),
    onNextClicked: () -> Unit,
) {
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is Success -> onNextClicked()
                else -> Unit
            }
        }
    }

    GoalTypeScreen(
        selectedGoalType = viewModel.selectedGoalType,
        onGoalTypeClicked = viewModel::onGoalTypeClicked,
        onNextClicked = viewModel::onNextClicked,
    )
}

@Composable
private fun GoalTypeScreen(
    selectedGoalType: GoalType,
    onGoalTypeClicked: (GoalType) -> Unit = {},
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
            text = stringResource(id = CoreString.lose_keep_or_gain_weight),
            style = MaterialTheme.typography.h3,
        )

        Spacer(modifier = Modifier.height(LocalSpacing.current.medium_16))

        Row {
            GoalTypeButton(
                labelRes = CoreString.lose,
                buttonGoalType = GoalType.LoseWeight,
                selectedGoalType = selectedGoalType,
                onGoalTypeClicked = onGoalTypeClicked,
            )

            Spacer(modifier = Modifier.width(LocalSpacing.current.medium_16))

            GoalTypeButton(
                labelRes = CoreString.keep,
                buttonGoalType = GoalType.KeepWeight,
                selectedGoalType = selectedGoalType,
                onGoalTypeClicked = onGoalTypeClicked,
            )

            Spacer(modifier = Modifier.width(LocalSpacing.current.medium_16))

            GoalTypeButton(
                labelRes = CoreString.gain,
                buttonGoalType = GoalType.GainWeight,
                selectedGoalType = selectedGoalType,
                onGoalTypeClicked = onGoalTypeClicked,
            )
        }
    }

    ActionButton(
        text = stringResource(id = CoreString.next),
        modifier = Modifier.align(Alignment.BottomEnd),
        onClick = onNextClicked,
    )
}

@Composable
private fun GoalTypeButton(
    labelRes: Int,
    buttonGoalType: GoalType,
    selectedGoalType: GoalType,
    onGoalTypeClicked: (GoalType) -> Unit,
) = SelectableButton(
    text = stringResource(id = labelRes),
    isSelected = selectedGoalType == buttonGoalType,
    color = MaterialTheme.colors.primaryVariant,
    selectedTextColor = Color.White,
    textStyle = MaterialTheme.typography.button.copy(fontWeight = FontWeight.Normal),
) { onGoalTypeClicked(buttonGoalType) }
