package nz.adjmunro.tracker.presentation.overview.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import nz.adjmunro.core.util.CoreDrawable
import nz.adjmunro.core.util.CoreString
import nz.adjmunro.core.util.UiText
import nz.adjmunro.coreui.LocalSpacing
import nz.adjmunro.tracker.domain.model.TrackedFood
import nz.adjmunro.tracker.presentation.components.NutrientInfo

@OptIn(ExperimentalCoilApi::class)
@Composable
fun TrackedFoodItem(
    trackedFood: TrackedFood,
    modifier: Modifier = Modifier,
    onDeleteClicked: () -> Unit = {},
) = Row(
    modifier = modifier
        .clip(shape = RoundedCornerShape(5.dp))
        .padding(all = LocalSpacing.current.tiny_4)
        .shadow(
            elevation = 1.dp,
            shape = RoundedCornerShape(5.dp)
        )
        .background(color = MaterialTheme.colors.surface)
        .padding(end = LocalSpacing.current.medium_16)
        .height(100.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
) {
    Image(
        painter = rememberImagePainter(
            data = trackedFood.imageUrl,
            builder = {
                crossfade(true)
                error(CoreDrawable.ic_burger)
                fallback(CoreDrawable.ic_burger)
            },
        ),
        contentDescription = trackedFood.name,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxHeight()
            .aspectRatio(1f)
            .clip(
                shape = RoundedCornerShape(
                    topStart = 5.dp,
                    bottomStart = 5.dp
                )
            ),
    )
    Spacer(modifier = Modifier.width(LocalSpacing.current.medium_16))
    Column(
        modifier = Modifier.weight(1f),
    ) {
        Text(
            text = trackedFood.name,
            style = MaterialTheme.typography.body1,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
        )
        Spacer(modifier = Modifier.height(LocalSpacing.current.tiny_4))
        Text(
            text = stringResource(
                id = CoreString.nutrient_info,
                trackedFood.amount,
                trackedFood.calories,
            ),
            style = MaterialTheme.typography.body2,
        )
    }
    Spacer(modifier = Modifier.width(LocalSpacing.current.medium_16))
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = stringResource(id = CoreString.delete),
            modifier = Modifier
                .align(Alignment.End)
                .clickable { onDeleteClicked() },
        )
        Spacer(modifier = Modifier.height(LocalSpacing.current.tiny_4))
        Row(verticalAlignment = Alignment.CenterVertically) {
            NutrientInfo(
                name = stringResource(id = CoreString.carbs),
                amount = trackedFood.carbs,
                unit = UiText.of(value = CoreString.grams),
                amountTextSize = 16.sp,
                unitTextSize = 12.sp,
                nameTextStyle = MaterialTheme.typography.body2,
            )
            Spacer(modifier = Modifier.width(LocalSpacing.current.small_8))
            NutrientInfo(
                name = stringResource(id = CoreString.protein),
                amount = trackedFood.protein,
                unit = UiText.of(value = CoreString.grams),
                amountTextSize = 16.sp,
                unitTextSize = 12.sp,
                nameTextStyle = MaterialTheme.typography.body2,
            )
            Spacer(modifier = Modifier.width(LocalSpacing.current.small_8))
            NutrientInfo(
                name = stringResource(id = CoreString.fat),
                amount = trackedFood.fat,
                unit = UiText.of(value = CoreString.grams),
                amountTextSize = 16.sp,
                unitTextSize = 12.sp,
                nameTextStyle = MaterialTheme.typography.body2,
            )
        }
    }
}
