package nz.adjmunro.tracker.presentation.overview.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import nz.adjmunro.core.util.CoreString
import nz.adjmunro.core.util.UiText
import nz.adjmunro.coreui.LocalSpacing
import nz.adjmunro.tracker.presentation.components.NutrientInfo
import nz.adjmunro.tracker.presentation.components.UnitDisplay
import nz.adjmunro.tracker.presentation.overview.Meal

@Composable
fun ExpandableMeal(
    meal: Meal,
    modifier: Modifier = Modifier,
    onToggleClicked: () -> Unit = {},
    content: @Composable () -> Unit = {},
) = Column(
    modifier = modifier,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onToggleClicked()
            }
            .padding(LocalSpacing.current.medium_16),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = meal.drawableRes),
            contentDescription = meal.name.asString(LocalContext.current)
        )
        Spacer(modifier = Modifier.width(LocalSpacing.current.medium_16))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = meal.name.asString(LocalContext.current),
                    style = MaterialTheme.typography.h3,
                )
                Icon(
                    imageVector = if (meal.isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (meal.isExpanded) stringResource(id = CoreString.collapse) else stringResource(id = CoreString.extend),
                )
            }
            Spacer(modifier = Modifier.height(LocalSpacing.current.small_8))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                UnitDisplay(
                    amount = meal.calories,
                    unit = UiText.of(value = CoreString.kcal),
                    amountTextSize = 30.sp,
                )
                Row {
                    NutrientInfo(
                        name = stringResource(id = CoreString.carbs),
                        amount = meal.carbs,
                        unit = UiText.of(value = CoreString.grams),
                    )
                    Spacer(modifier = Modifier.width(LocalSpacing.current.small_8))
                    NutrientInfo(
                        name = stringResource(id = CoreString.protein),
                        amount = meal.protein,
                        unit = UiText.of(value = CoreString.grams),
                    )
                    Spacer(modifier = Modifier.width(LocalSpacing.current.small_8))
                    NutrientInfo(
                        name = stringResource(id = CoreString.fat),
                        amount = meal.fat,
                        unit = UiText.of(value = CoreString.grams),
                    )
                }
            }
            Spacer(modifier = Modifier.height(LocalSpacing.current.medium_16))
            AnimatedVisibility(visible = meal.isExpanded) {
                content()
            }
        }
    }
}
