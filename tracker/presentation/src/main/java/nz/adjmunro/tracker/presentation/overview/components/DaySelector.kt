package nz.adjmunro.tracker.presentation.overview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import nz.adjmunro.core.util.CoreString
import java.time.LocalDate

@Composable
fun DaySelector(
    date: LocalDate,
    modifier: Modifier = Modifier,
    onPreviousDayClicked: () -> Unit = {},
    onNextDayClicked: () -> Unit = {},
) = Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
) {
    IconButton(onClick = onPreviousDayClicked) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = stringResource(id = CoreString.previous_day)
        )
    }
    Text(
        text = parseDateText(date),
        style = MaterialTheme.typography.h2,
    )
    IconButton(onClick = onNextDayClicked) {
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = stringResource(id = CoreString.next_day)
        )
    }
}
