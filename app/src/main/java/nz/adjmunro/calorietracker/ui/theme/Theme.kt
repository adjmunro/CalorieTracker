package nz.adjmunro.calorietracker.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import nz.adjmunro.coreui.BrightGreen
import nz.adjmunro.coreui.DarkGray
import nz.adjmunro.coreui.DarkGreen
import nz.adjmunro.coreui.LightGray
import nz.adjmunro.coreui.LocalSpacing
import nz.adjmunro.coreui.MediumGray
import nz.adjmunro.coreui.Orange
import nz.adjmunro.coreui.Spacing
import nz.adjmunro.coreui.TextWhite

private val DarkColorPalette = darkColors(
    primary = BrightGreen,
    primaryVariant = DarkGreen,
    secondary = Orange,
    background = MediumGray,
    onBackground = TextWhite,
    surface = LightGray,
    onSurface = TextWhite,
    onPrimary = Color.White,
    onSecondary = Color.White,
)

private val LightColorPalette = lightColors(
    primary = BrightGreen,
    primaryVariant = DarkGreen,
    secondary = Orange,
    background = Color.White,
    onBackground = DarkGray,
    surface = Color.White,
    onSurface = DarkGray,
    onPrimary = Color.White,
    onSecondary = Color.White,
)

@Composable
fun CaloryTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit,
) {

    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    CompositionLocalProvider(
        LocalSpacing provides Spacing(),
    ) {
        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content,
        )
    }
}
