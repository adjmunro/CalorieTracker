package nz.adjmunro.tracker.presentation.components

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nz.adjmunro.core.util.CoreString
import nz.adjmunro.core.util.UiText
import nz.adjmunro.coreui.CarbColor
import nz.adjmunro.coreui.FatColor
import nz.adjmunro.coreui.LocalSpacing
import nz.adjmunro.coreui.ProteinColor
import nz.adjmunro.tracker.presentation.overview.TrackerOverviewState
import nz.adjmunro.tracker.presentation.overview.components.NutrientBarInfo
import nz.adjmunro.tracker.presentation.overview.components.NutrientsBar

@Composable
fun NutrientsHeader(
    state: TrackerOverviewState,
    modifier: Modifier = Modifier,
) = Column(
    modifier = modifier
        .fillMaxWidth()
        .clip(
            shape = RoundedCornerShape(
                bottomStart = 50.dp,
                bottomEnd = 50.dp,
            ),
        )
        .background(color = MaterialTheme.colors.primary)
        .padding(
            horizontal = LocalSpacing.current.large_24,
            vertical = LocalSpacing.current.huge_32,
        ),
) {
    val animatedCalorieCount = animateIntAsState(
        targetValue = state.totalCalories,
        label = "animatedCalorieCount",
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        UnitDisplay(
            amount = animatedCalorieCount.value,
            modifier = Modifier.align(Alignment.Bottom),
            unit = UiText.of(CoreString.kcal),
            amountColor = MaterialTheme.colors.onPrimary,
            amountTextSize = 40.sp,
            unitColor = MaterialTheme.colors.onPrimary,
        )
        Column {
            Text(
                text = stringResource(id = CoreString.your_goal),
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.body2,
            )
            UnitDisplay(
                amount = state.caloriesGoal,
                unit = UiText.of(CoreString.kcal),
                amountColor = MaterialTheme.colors.onPrimary,
                amountTextSize = 40.sp,
                unitColor = MaterialTheme.colors.onPrimary,
            )
        }
    }

    Spacer(modifier = Modifier.height(LocalSpacing.current.small_8))

    NutrientsBar(
        carbs = state.totalCarbs,
        protein = state.totalProtein,
        fat = state.totalFat,
        calories = state.totalCalories,
        calorieGoal = state.caloriesGoal,
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp),
    )

    Spacer(modifier = Modifier.height(LocalSpacing.current.large_24))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        NutrientBarInfo(
            value = state.totalCarbs,
            goal = state.carbsGoal,
            name = UiText.of(CoreString.carbs),
            color = CarbColor,
            modifier = Modifier.size(90.dp),
        )
        NutrientBarInfo(
            value = state.totalProtein,
            goal = state.proteinGoal,
            name = UiText.of(CoreString.protein),
            color = ProteinColor,
            modifier = Modifier.size(90.dp),
        )
        NutrientBarInfo(
            value = state.totalFat,
            goal = state.fatGoal,
            name = UiText.of(CoreString.fat),
            color = FatColor,
            modifier = Modifier.size(90.dp),
        )
    }
}
