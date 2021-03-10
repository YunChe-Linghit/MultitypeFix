package oms.mmc.fast.multitype

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.drakeet.multitype.ItemViewDelegate

/**
 * <b>Create Date:</b> 2020/7/27 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b> ItemViewBinder 封装 DataBinding <br>
 */
abstract class RDItemViewBinder<T, B : ViewDataBinding?> : ItemViewDelegate<T, RViewHolder>() {

    protected abstract fun onInflaterViewId(): Int

    /**
     * 注意：
     * RecyclerView 中的数据有位置改变（比如删除）时一般不会重新调用 onBindViewHolder() 方法，除非这个元素不可用。
     * 为了实时获取元素的位置，我们通过 ViewHolder.getAdapterPosition() 方法。
     *
     * @param binding .
     * @param item    .
     * @param holder  .
     */
    protected abstract fun onBindViewHolder(binding: B?, item: T, holder: RViewHolder)

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup
    ): RViewHolder {
        val binding: B? = DataBindingUtil.inflate<B>(
            LayoutInflater.from(context),
            onInflaterViewId(),
            parent,
            false
        )
        return RViewHolder(binding!!.root)
    }

    override fun onBindViewHolder(holder: RViewHolder, item: T) {
        val binding: B? = DataBindingUtil.getBinding<B>(holder.itemView)
        this.onBindViewHolder(binding, item, holder)
        binding?.executePendingBindings()
    }
}