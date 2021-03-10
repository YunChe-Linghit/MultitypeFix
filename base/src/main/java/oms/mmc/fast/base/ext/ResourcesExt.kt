package oms.mmc.fast.base.ext

import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.core.content.ContextCompat
import oms.mmc.fast.base.util.ContextProvider

/**
 * <b>Create Date:</b> 2020/9/16 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b>  <br>
 */
val Float.dp: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        ContextProvider.getInstance().context.resources.displayMetrics
    ) + 0.5f

val Int.dp: Int
    get() = (TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        ContextProvider.getInstance().context.resources.displayMetrics
    ) + 0.5f).toInt()

val Float.sp: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this,
        ContextProvider.getInstance().context.resources.displayMetrics
    ) + 0.5f

val Int.sp: Int
    get() = (TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this.toFloat(),
        ContextProvider.getInstance().context.resources.displayMetrics
    ) + 0.5f).toInt()

fun Int.getDrawable(): Drawable? {
    return ContextCompat.getDrawable(ContextProvider.getInstance().context, this)
}

fun Int.getColor(): Int {
    return ContextCompat.getColor(ContextProvider.getInstance().context, this)
}

fun Int.getString(): String {
    return ContextProvider.getInstance().context.getString(this)
}

fun Int.getString(vararg formatArgs: Any): String {
    return ContextProvider.getInstance().context.getString(this, *formatArgs)
}

fun Int.getStringArray(): Array<out String> {
    return ContextProvider.getInstance().context.resources.getStringArray(this)
}

fun Int.getInteger(): Int {
    return ContextProvider.getInstance().context.resources.getInteger(this)
}