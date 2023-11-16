package nz.adjmunro.tracker.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import nz.adjmunro.core.util.UiText
import nz.adjmunro.coreui.LocalSpacing

@Composable
fun UnitDisplay(
    amount: Int,
    unit: UiText,
    modifier: Modifier = Modifier,
    amountTextSize: TextUnit = 20.sp,
    amountColor: Color = MaterialTheme.colors.onBackground,
    unitTextSize: TextUnit = 14.sp,
    unitColor: Color = MaterialTheme.colors.onBackground,
) = Row(modifier = modifier) {
    Text(
        text = amount.toString(),
        modifier = Modifier.alignBy(LastBaseline),
        color = amountColor,
        fontSize = amountTextSize,
        style = MaterialTheme.typography.h1,
    )

    Spacer(modifier = Modifier.width(LocalSpacing.current.tiny_4))

    Text(
        text = unit.asString(LocalContext.current),
        modifier = Modifier.alignBy(LastBaseline),
        color = unitColor,
        fontSize = unitTextSize,
        style = MaterialTheme.typography.body1,
    )
}
