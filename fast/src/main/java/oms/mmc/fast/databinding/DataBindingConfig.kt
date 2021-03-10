package oms.mmc.fast.databinding

import android.util.SparseArray
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView

/**
 * <b>Create Date:</b> 2020/7/23 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b>
 * TODO tip:
 * 将 DataBinding 实例限制于 base 页面中，默认不向子类暴露，
 * 通过这样的方式，来彻底解决 视图调用的一致性问题，
 * 如此，视图刷新的安全性将和基于函数式编程的 Jetpack Compose 持平。
 * 而 DataBindingConfig 就是在这样的背景下，用于为 base 页面中的 DataBinding 提供绑定项。
 *
 * 如果这样说还不理解的话，详见 https://xiaozhuanlan.com/topic/9816742350 和 https://xiaozhuanlan.com/topic/2356748910
 * <br>
 */
class DataBindingConfig(
    val viewModel: ViewModel,
    val adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>? = null,
    val itemDecoration: RecyclerView.ItemDecoration? = null
) {
    private val bindingParams = SparseArray<Any?>()

    fun getBindingParams(): SparseArray<Any?> {
        return bindingParams
    }

    fun addBindingParam(variableId: Int, any: Any?): DataBindingConfig {
        if (bindingParams[variableId] == null) {
            bindingParams.put(variableId, any)
        }
        return this
    }

}