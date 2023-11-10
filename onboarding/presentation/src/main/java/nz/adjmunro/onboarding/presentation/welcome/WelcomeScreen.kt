package nz.adjmunro.onboarding.presentation.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import nz.adjmunro.core.R
import nz.adjmunro.core.navigation.Routes
import nz.adjmunro.core.util.UiEvent.Navigate
import nz.adjmunro.coreui.LocalSpacing
import nz.adjmunro.onboarding.presentation.welcome.components.ActionButton

@Composable
fun WelcomeScreen(
    onNavigate: (Navigate) -> Unit = {},
) = Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(LocalSpacing.current.medium_16),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
) {
    Text(
        text = stringResource(id = R.string.welcome_text),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h1,
    )

    Spacer(modifier = Modifier.height(LocalSpacing.current.medium_16))

    ActionButton(
        text = stringResource(id = R.string.next),
        modifier = Modifier.align(Alignment.CenterHorizontally),
    ) {
        onNavigate(Navigate(Routes.AGE))
    }

}
