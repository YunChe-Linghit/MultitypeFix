package oms.mmc.fast.base.ext

import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView
import oms.mmc.fast.base.util.ClickUtils

/**
 * <b>Create Date:</b> 2020/9/16 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b>  <br>
 */
fun View.setOnClickDebouncing(listener: View.OnClickListener) {
    ClickUtils.applySingleDebouncing(this, listener)
}

fun View.setOnClickDebouncing(listener: (View) -> Unit) {
    ClickUtils.applySingleDebouncing(this) {
        listener.invoke(it)
    }
}

fun TextView.setStrikeThrough() {
    paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
}

fun TextView.setUnderLine() {
    paint.flags = Paint.UNDERLINE_TEXT_FLAG
}

fun TextView.setLeftDrawable(drawable: Drawable?) {
    setCompoundDrawables(drawable, null, null, null)
}

fun TextView.setTopDrawable(drawable: Drawable?) {
    setCompoundDrawables(null, drawable, null, null)
}

fun TextView.setRightDrawable(drawable: Drawable?) {
    setCompoundDrawables(null, null, drawable, null)
}

fun TextView.setBottomDrawable(drawable: Drawable?) {
    setCompoundDrawables(null, null, null, drawable)
}