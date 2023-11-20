package nz.adjmunro.tracker.presentation.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import nz.adjmunro.core.util.CoreString
import nz.adjmunro.coreui.LocalSpacing

@Composable
fun SearchTextField(
    text: String,
    modifier: Modifier = Modifier,
    hint: String = stringResource(id = CoreString.search),
    shouldShowHint: Boolean = false,
    onFocusChanged: (FocusState) -> Unit = { _ -> },
    onSearch: () -> Unit = {},
    onValueChanged: (String) -> Unit = { _ -> },
) = Box(modifier = modifier) {
    BasicTextField(
        value = text,
        singleLine = true,
        onValueChange = onValueChanged,
        modifier = Modifier
            .clip(RoundedCornerShape(5.dp))
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(5.dp)
            )
            .background(MaterialTheme.colors.surface)
            .fillMaxWidth()
            .padding(LocalSpacing.current.medium_16)
            .padding(end = LocalSpacing.current.medium_16)
            .onFocusChanged(onFocusChanged),
        keyboardActions = KeyboardActions(onSearch = {
            onSearch()
            defaultKeyboardAction(ImeAction.Search)
        }),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
    )

    if (shouldShowHint) {
        Text(
            text = hint,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Light,
            color = Color.LightGray,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = LocalSpacing.current.medium_16)
        )
    }

    IconButton(
        onClick = onSearch,
        modifier = Modifier.align(Alignment.CenterEnd)
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = stringResource(id = CoreString.search),
        )
    }
}
