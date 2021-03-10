package oms.mmc.fastlist.viewmodel

import android.content.Context
import com.scwang.smartrefresh.layout.api.RefreshLayout
import oms.mmc.fast.vm.BaseViewModel

/**
 * <b>Create Date:</b> 2020/9/9 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b>  <br>
 */
abstract class BaseFastViewModel : BaseViewModel() {
    lateinit var context: Context
    lateinit var list: MutableList<Any>
    lateinit var loadDataCallback: () -> Unit
    lateinit var loadMoreCallback: () -> Unit
    lateinit var handleDataCallback: (List<Any>?, Int) -> Unit
    lateinit var handleErrorCallback: () -> Unit
    lateinit var notifyDataSetChangedCallback: (List<Any>) -> Unit

    abstract fun onLoadData(recyclerView: RefreshLayout, currentPage: Int)

    fun loadData() {
        loadDataCallback.invoke()
    }

    fun loadMore() {
        loadMoreCallback.invoke()
    }

    fun handleData(data: List<Any>?, pageSize: Int = 10) {
        handleDataCallback.invoke(data, pageSize)
    }

    fun handleError() {
        handleErrorCallback.invoke()
    }

    fun notifyDataSetChanged(list: List<Any>) {
        notifyDataSetChangedCallback.invoke(list)
    }
}