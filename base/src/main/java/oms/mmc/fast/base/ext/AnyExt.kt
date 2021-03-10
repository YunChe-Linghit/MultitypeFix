package oms.mmc.fast.base.ext

import android.os.Build
import android.text.Html
import android.text.Spanned
import android.text.TextUtils

/**
 * <b>Create Date:</b> 2020/7/23 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b>  <br>
 */
fun Any?.isNull(): Boolean {
    return this == null
}

fun Any?.isNotNull(): Boolean {
    return !isNull()
}

/**
 * 判断字符串是否不为null，不为空字符串
 */
fun TextUtils.isNotEmpty(text: CharSequence): Boolean {
    return !TextUtils.isEmpty(text)
}

fun String.fromHtml(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, 0)
    } else {
        Html.fromHtml(this)
    }
}