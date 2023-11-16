package nz.adjmunro.tracker.presentation.overview.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import nz.adjmunro.core.util.UiText
import nz.adjmunro.tracker.presentation.components.UnitDisplay

@Composable
fun NutrientBarInfo(
    value: Int,
    goal: Int,
    name: UiText,
    color: Color,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 8.dp,
) {
    val background = MaterialTheme.colors.background
    val foreground = MaterialTheme.colors.onPrimary
    val goalExceededColor = MaterialTheme.colors.error

    val angleRatio = remember(key1 = value) {
        Animatable(initialValue = 0f)
    }

    LaunchedEffect(key1 = value) {
        angleRatio.animateTo(
            targetValue = if (goal > 0) value / goal.toFloat() else 0f,
            animationSpec = tween(durationMillis = 300)
        )
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(ratio = 1f),
        ) {
            // Background Ring
            drawArc(
                color = if (value <= goal) background else goalExceededColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false, // Outline, not pie-slice connected to centre
                style = Stroke(
                    width = strokeWidth.toPx(),
                    cap = StrokeCap.Round,
                )
            )

            // Sweep Ring
            if (value <= goal) {
                drawArc(
                    color = color,
                    startAngle = 90f,
                    sweepAngle = 360f * angleRatio.value,
                    useCenter = false,
                    size = size,
                    style = Stroke(
                        width = strokeWidth.toPx(),
                        cap = StrokeCap.Round,
                    )
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val textColor = remember(key1 = value) {
                if (value <= goal) foreground
                else goalExceededColor
            }
            UnitDisplay(
                amount = value,
                unit = UiText.of(value = nz.adjmunro.core.R.string.grams),
                amountColor = textColor,
                unitColor = textColor,
            )
            Text(
                text = name.asString(LocalContext.current),
                color = textColor,
                fontWeight = FontWeight.Light,
                style = MaterialTheme.typography.body1,
            )
        }
    }

}
