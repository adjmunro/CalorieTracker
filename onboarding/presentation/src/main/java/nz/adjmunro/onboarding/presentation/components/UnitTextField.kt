package nz.adjmunro.onboarding.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import nz.adjmunro.coreui.LocalSpacing

@Composable
fun UnitTextField(
    value: String,
    modifier: Modifier = Modifier,
    unit: String = "",
    textStyle: TextStyle = TextStyle(
        color = MaterialTheme.colors.primaryVariant,
        fontSize = 70.sp,
    ),
    onValueChange: (String) -> Unit = {},
) = Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.Center,
) {
    BasicTextField(
        value = value,
        modifier = Modifier
            .width(IntrinsicSize.Min)
            .alignBy(LastBaseline),
        onValueChange = onValueChange,
        textStyle = textStyle,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
        ),
        singleLine = true,
    )

    Spacer(modifier = Modifier.width(LocalSpacing.current.small_8))

    Text(text = unit, modifier = Modifier.alignBy(LastBaseline))
}
