package oms.mmc.fast.bindingadapter

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import oms.mmc.fast.R
import oms.mmc.fast.base.ext.fromHtml
import oms.mmc.fast.base.ext.getString
import oms.mmc.fast.base.ext.setStrikeThrough
import oms.mmc.fast.base.ext.setUnderLine
import oms.mmc.fast.base.util.ClickUtils

/**
 * <b>Create Date:</b> 2020/9/9 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b> common view binding adapter <br>
 */
object CommonBindingAdapter {
    @JvmStatic
    @BindingAdapter("activity", "avatarUrl", "placeHolder", requireAll = false)
    fun loadAvatar(view: ImageView, activity: Context?, url: String?, placeHolder: Int) {
        if (activity is Activity) {
            Glide.with(activity).load(url)
                .apply(RequestOptions().error(placeHolder).placeholder(placeHolder)).into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("activity", "imageUrl", "placeHolder", requireAll = false)
    fun loadImage(view: ImageView, activity: Context?, url: String?, placeHolder: Int) {
        if (activity is Activity) {
            Glide.with(activity).load(url)
                .apply(RequestOptions().error(placeHolder).placeholder(placeHolder)).into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("visible")
    fun visible(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("visibility")
    fun visibility(view: View, visibility: Boolean) {
        view.visibility = if (visibility) View.VISIBLE else View.INVISIBLE
    }

    @JvmStatic
    @BindingAdapter("showDrawable", "drawableShowed", requireAll = false)
    fun showDrawable(view: ImageView, showDrawable: Boolean, drawableShowed: Int) {
        view.setImageResource(if (showDrawable) drawableShowed else android.R.color.transparent)
    }

    @JvmStatic
    @BindingAdapter("textColor")
    fun setTextColor(textView: TextView, textColorRes: Int) {
        textView.setTextColor(ContextCompat.getColor(textView.context, textColorRes))
    }

    @JvmStatic
    @BindingAdapter("imageRes")
    fun setImageRes(imageView: ImageView, imageRes: Int) {
        imageView.setImageResource(imageRes)
    }

    @JvmStatic
    @BindingAdapter("bgRes")
    fun setImageRes(view: View, bgRes: Int) {
        view.setBackgroundResource(bgRes)
    }

    @JvmStatic
    @BindingAdapter("selected")
    fun selected(view: View, select: Boolean) {
        view.isSelected = select
    }

    @JvmStatic
    @BindingAdapter("onClickWithDebouncing")
    fun onClickWithDebouncing(view: View, clickListener: View.OnClickListener?) {
        ClickUtils.applySingleDebouncing(view, clickListener)
    }

    @JvmStatic
    @BindingAdapter("adjustWidth")
    fun adjustWidth(view: View, adjustWidth: Int) {
        val params = view.layoutParams
        params.width = adjustWidth
        view.layoutParams = params
    }

    @JvmStatic
    @BindingAdapter("adjustHeight")
    fun adjustHeight(view: View, adjustHeight: Int) {
        val params = view.layoutParams
        params.height = adjustHeight
        view.layoutParams = params
    }

    @JvmStatic
    @BindingAdapter("fromHtml")
    fun setFromHtml(textView: TextView, source: String?) {
        source?.let {
            textView.text = source.fromHtml()
        }
    }

    @JvmStatic
    @BindingAdapter("textStyleExt")
    fun setTextStyleExt(textView: TextView, style: String?) {
        when (style) {
            R.string.strikeThrough.getString() -> textView.setStrikeThrough()
            R.string.underLine.getString() -> textView.setUnderLine()
        }
    }
}