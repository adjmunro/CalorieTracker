package nz.adjmunro.coreui

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing(
    val tiny_4: Dp = 4.dp,
    val small_8: Dp = 8.dp,
    val medium_16: Dp = 16.dp,
    val large_24: Dp = 24.dp,
    val huge_32: Dp = 32.dp,
    val massive_40: Dp = 40.dp,
)

val LocalSpacing = compositionLocalOf { Spacing() }
