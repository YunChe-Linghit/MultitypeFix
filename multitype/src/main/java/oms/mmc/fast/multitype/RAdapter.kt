package oms.mmc.fast.multitype

import androidx.recyclerview.widget.DiffUtil
import com.drakeet.multitype.MultiTypeAdapter
import com.drakeet.multitype.Types

/**
 * <b>Create Date:</b> 2020/7/27 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b> adapter 封装 DiffUtil <br>
 */
class RAdapter : MultiTypeAdapter {

    constructor()
    constructor(items: List<Any>) : super(items)
    constructor(items: List<Any>, initialCapacity: Int) : super(
        items,
        initialCapacity
    )

    constructor(
        items: List<Any>,
        initialCapacity: Int,
        types: Types
    ) : super(items, initialCapacity, types)

    @JvmOverloads
    fun swapData(newItems: List<Any>, callback: DiffUtil.Callback? = null) {
        var cb = callback
        if (cb == null) {
            cb = DiffCallback(items, newItems)
        }
        val diffResult = DiffUtil.calculateDiff(cb, false)
        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    class DiffCallback internal constructor(
        private val oldList: List<Any>,
        private val newList: List<Any>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(
            oldItemPosition: Int,
            newItemPosition: Int
        ): Boolean {
            return oldList[oldItemPosition] === newList[newItemPosition]
        }

        override fun areContentsTheSame(
            oldItemPosition: Int,
            newItemPosition: Int
        ): Boolean {
            // 可以通过重写 object 对象的 equals() 方法实现对象比较
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

    }
}