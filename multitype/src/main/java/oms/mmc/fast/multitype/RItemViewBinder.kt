package oms.mmc.fast.multitype

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.drakeet.multitype.ItemViewDelegate

/**
 * <b>Create Date:</b> 2020/7/27 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b> ItemViewBinder 封装 ItemViewDelegate <br>
 */
abstract class RItemViewBinder<T> : ItemViewDelegate<T, RViewHolder>() {

    protected abstract fun onInflaterViewId(): Int

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup
    ): RViewHolder {
        val view = LayoutInflater.from(context).inflate(onInflaterViewId(), parent, false)
        return RViewHolder(view)
    }
}