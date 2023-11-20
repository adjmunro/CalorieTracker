package nz.adjmunro.onboarding.presentation.activity

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
import nz.adjmunro.core.domain.models.ActivityLevel
import nz.adjmunro.core.util.CoreString
import nz.adjmunro.core.util.UiEvent.Navigate
import nz.adjmunro.coreui.LocalSpacing
import nz.adjmunro.onboarding.presentation.components.ActionButton
import nz.adjmunro.onboarding.presentation.components.SelectableButton

@Composable
fun ActivityLevelScreen(
    viewModel: ActivityLevelViewModel = hiltViewModel(),
    onNavigate: (Navigate) -> Unit,
) {
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    ActivityLevelScreen(
        selectedActivityLevel = viewModel.selectedActivityLevel,
        onActivityLevelClicked = viewModel::onActivityLevelClicked,
        onNextClicked = viewModel::onNextClicked,
    )
}

@Composable
private fun ActivityLevelScreen(
    selectedActivityLevel: ActivityLevel,
    onActivityLevelClicked: (ActivityLevel) -> Unit = {},
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
            text = stringResource(id = CoreString.whats_your_activity_level),
            style = MaterialTheme.typography.h3,
        )

        Spacer(modifier = Modifier.height(LocalSpacing.current.medium_16))

        Row {
            ActivityLevelButton(
                labelRes = CoreString.low,
                buttonActivityLevel = ActivityLevel.LowActivity,
                selectedActivityLevel = selectedActivityLevel,
                onActivityLevelClicked = onActivityLevelClicked,
            )

            Spacer(modifier = Modifier.width(LocalSpacing.current.medium_16))

            ActivityLevelButton(
                labelRes = CoreString.medium,
                buttonActivityLevel = ActivityLevel.MediumActivity,
                selectedActivityLevel = selectedActivityLevel,
                onActivityLevelClicked = onActivityLevelClicked,
            )

            Spacer(modifier = Modifier.width(LocalSpacing.current.medium_16))

            ActivityLevelButton(
                labelRes = CoreString.high,
                buttonActivityLevel = ActivityLevel.HighActivity,
                selectedActivityLevel = selectedActivityLevel,
                onActivityLevelClicked = onActivityLevelClicked,
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
private fun ActivityLevelButton(
    labelRes: Int,
    buttonActivityLevel: ActivityLevel,
    selectedActivityLevel: ActivityLevel,
    onActivityLevelClicked: (ActivityLevel) -> Unit,
) = SelectableButton(
    text = stringResource(id = labelRes),
    isSelected = selectedActivityLevel == buttonActivityLevel,
    color = MaterialTheme.colors.primaryVariant,
    selectedTextColor = Color.White,
    textStyle = MaterialTheme.typography.button.copy(fontWeight = FontWeight.Normal),
) { onActivityLevelClicked(buttonActivityLevel) }
