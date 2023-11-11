package nz.adjmunro.onboarding.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import nz.adjmunro.coreui.LocalSpacing

@Composable
fun SelectableButton(
    text: String,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    color: Color = MaterialTheme.colors.primary,
    selectedTextColor: Color = MaterialTheme.colors.onPrimary,
    textStyle: TextStyle = MaterialTheme.typography.button,
    shape: Shape = RoundedCornerShape(100.dp),
    onClick: () -> Unit = {},
) = Box(
    contentAlignment = Alignment.Center,
    modifier = modifier
        .clip(shape = shape)
        .border(width = 2.dp, color = color, shape = shape)
        .background(
            color = if (isSelected) color else Color.Transparent,
            shape = shape,
        )
        .clickable {
            onClick()
        }
        .padding(LocalSpacing.current.medium_16),
) {
    Text(
        text = text,
        style = textStyle,
        color = if (isSelected) selectedTextColor else color,
    )
}
