package nz.adjmunro.core.util

import android.content.Context
import androidx.annotation.StringRes

sealed class UiText {
    data class DynamicString(val text: String) : UiText()
    data class StringResource(@StringRes val resId: Int) : UiText()

    fun asString(context: Context): String = when (this) {
        is DynamicString -> text
        is StringResource -> context.getString(resId)
    }

    companion object {
        fun of(value: String): DynamicString = DynamicString(text = value)
        fun of(@StringRes value: Int): StringResource = StringResource(resId = value)
    }
}
