package nz.adjmunro.onboarding.presentation.gender

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
import nz.adjmunro.core.domain.models.Gender
import nz.adjmunro.core.util.CoreString
import nz.adjmunro.core.util.UiEvent.Success
import nz.adjmunro.coreui.LocalSpacing
import nz.adjmunro.onboarding.presentation.components.ActionButton
import nz.adjmunro.onboarding.presentation.components.SelectableButton

@Composable
fun GenderScreen(
    viewModel: GenderViewModel = hiltViewModel(),
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

    GenderScreen(
        selectedGender = viewModel.selectedGender,
        onGenderClicked = viewModel::onGenderClicked,
        onNextClicked = viewModel::onNextClicked,
    )
}

@Composable
private fun GenderScreen(
    selectedGender: Gender,
    onGenderClicked: (Gender) -> Unit = {},
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
            text = stringResource(id = CoreString.whats_your_gender),
            style = MaterialTheme.typography.h3,
        )

        Spacer(modifier = Modifier.height(LocalSpacing.current.medium_16))

        Row {
            SelectableButton(
                text = stringResource(id = CoreString.male),
                isSelected = selectedGender is Gender.Male,
                color = MaterialTheme.colors.primaryVariant,
                selectedTextColor = Color.White,
                textStyle = MaterialTheme.typography.button.copy(fontWeight = FontWeight.Normal),
            ) { onGenderClicked(Gender.Male) }

            Spacer(modifier = Modifier.width(LocalSpacing.current.medium_16))

            SelectableButton(
                text = stringResource(id = CoreString.female),
                isSelected = selectedGender is Gender.Female,
                color = MaterialTheme.colors.primaryVariant,
                selectedTextColor = Color.White,
                textStyle = MaterialTheme.typography.button.copy(fontWeight = FontWeight.Normal),
            ) { onGenderClicked(Gender.Female) }
        }
    }

    ActionButton(
        text = stringResource(id = CoreString.next),
        modifier = Modifier.align(Alignment.BottomEnd),
        onClick = onNextClicked,
    )
}
