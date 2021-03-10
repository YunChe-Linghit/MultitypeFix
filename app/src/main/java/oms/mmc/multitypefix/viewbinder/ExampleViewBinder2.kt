package oms.mmc.multitypefix.viewbinder

import android.util.Log
import oms.mmc.fast.multitype.RItemViewBinder
import oms.mmc.fast.multitype.RViewHolder
import oms.mmc.multitypefix.R

/**
 * <b>Create Date:</b> 2020/9/4 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b>  <br>
 */
class ExampleViewBinder2(private val listener: (() -> Unit)? = null) : RItemViewBinder<Int>() {
    override fun onInflaterViewId(): Int {
        return R.layout.fast_list_example_item2
    }

    override fun onBindViewHolder(holder: RViewHolder, item: Int) {
        Log.d(
            "onBindViewHolder",
            "onBindViewHolder:${this},position:${holder.layoutPosition},item:${item}"
        )
        holder.apply {
            itemView.setOnClickListener {
                listener?.invoke()
            }
        }
    }
}