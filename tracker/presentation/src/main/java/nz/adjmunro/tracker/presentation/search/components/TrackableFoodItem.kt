package nz.adjmunro.tracker.presentation.search.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.ImeAction.Companion
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import nz.adjmunro.core.util.CoreDrawable
import nz.adjmunro.core.util.CoreString
import nz.adjmunro.core.util.UiText
import nz.adjmunro.coreui.LocalSpacing
import nz.adjmunro.tracker.presentation.components.NutrientInfo
import nz.adjmunro.tracker.presentation.search.TrackableFoodUiState

@OptIn(ExperimentalCoilApi::class)
@Composable
fun TrackableFoodItem(
    trackableFoodUiState: TrackableFoodUiState,
    modifier: Modifier = Modifier,
    onAmountChanged: (String) -> Unit,
    onTrack: () -> Unit,
    onClick: () -> Unit,
) {
    val food = trackableFoodUiState.food
    val spacing = LocalSpacing.current

    Column(
        modifier = modifier
            .clip(shape = RoundedCornerShape(5.dp))
            .padding(all = spacing.tiny_4)
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(5.dp)
            )
            .background(MaterialTheme.colors.surface)
            .clickable { onClick() }
            .padding(end = spacing.medium_16),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = CenterVertically
        ) {
            Row(modifier = Modifier.weight(1f)) {
                Image(
                    painter = rememberImagePainter(
                        data = food.imageUrl,
                        builder = {
                            crossfade(true)
                            error(CoreDrawable.ic_burger)
                            fallback(CoreDrawable.ic_burger)
                        },
                    ),
                    contentDescription = food.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(topStart = 5.dp))
                )
                Spacer(modifier = Modifier.width(spacing.medium_16))
                Column(modifier = Modifier.align(CenterVertically)) {
                    Text(
                        text = food.name,
                        style = MaterialTheme.typography.body1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(modifier = Modifier.height(spacing.small_8))
                    Text(
                        text = stringResource(
                            id = CoreString.kcal_per_100g,
                            food.caloriesPer100g,
                        ),
                        style = MaterialTheme.typography.body2,
                    )
                }
            }
            Row {
                NutrientInfo(
                    name = stringResource(id = CoreString.carbs),
                    amount = food.carbsPer100g,
                    unit = UiText.of(value = CoreString.grams),
                    amountTextSize = 16.sp,
                    unitTextSize = 12.sp,
                    nameTextStyle = MaterialTheme.typography.body2,
                )
                Spacer(modifier = Modifier.width(spacing.small_8))
                NutrientInfo(
                    name = stringResource(id = CoreString.protein),
                    amount = food.proteinPer100g,
                    unit = UiText.of(value = CoreString.grams),
                    amountTextSize = 16.sp,
                    unitTextSize = 12.sp,
                    nameTextStyle = MaterialTheme.typography.body2,
                )
                Spacer(modifier = Modifier.width(spacing.small_8))
                NutrientInfo(
                    name = stringResource(id = CoreString.fat),
                    amount = food.fatPer100g,
                    unit = UiText.of(value = CoreString.grams),
                    amountTextSize = 16.sp,
                    unitTextSize = 12.sp,
                    nameTextStyle = MaterialTheme.typography.body2,
                )
            }
        }
        AnimatedVisibility(visible = trackableFoodUiState.isExpanded) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.medium_16),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = CenterVertically,
            ) {
                Row {
                    BasicTextField(
                        value = trackableFoodUiState.amount,
                        onValueChange = onAmountChanged,
                        keyboardOptions = KeyboardOptions(
                            imeAction = if (trackableFoodUiState.amount.isNotBlank()) ImeAction.Done
                            else Companion.Default,
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            onTrack()
                            defaultKeyboardAction(Companion.Done)
                        }),
                        singleLine = true,
                        modifier = Modifier
                            .border(
                                shape = RoundedCornerShape(5.dp),
                                width = 0.5.dp,
                                color = MaterialTheme.colors.onSurface,
                            )
                            .alignBy(LastBaseline)
                            .padding(spacing.medium_16),
                    )
                    Spacer(modifier = Modifier.width(spacing.tiny_4))
                    Text(
                        text = stringResource(id = CoreString.grams),
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.alignBy(LastBaseline),
                    )
                }
                IconButton(
                    onClick = onTrack,
                    enabled = trackableFoodUiState.amount.isNotBlank(),
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(id = CoreString.track),
                    )
                }
            }
        }
    }
}
