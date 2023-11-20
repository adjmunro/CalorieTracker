package nz.adjmunro.tracker.presentation.overview.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import nz.adjmunro.core.util.CoreString
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun parseDateText(date: LocalDate): String {
    val today = LocalDate.now()
    return when (date) {
        today -> stringResource(id = CoreString.today)
        today.minusDays(1) -> stringResource(id = CoreString.yesterday)
        today.plusDays(1) -> stringResource(id = CoreString.tomorrow)
        else -> DateTimeFormatter.ofPattern("dd LLLL")
            .format(date)
    }
}
