package oms.mmc.fastlist.listener

import com.scwang.smartrefresh.layout.api.RefreshLayout

/**
 * <b>Create Date:</b> 2020/9/7 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b> 加载更多接口 <br>
 */
interface OnLoadMoreListener {
    fun onLoadMore(recyclerView: RefreshLayout, position: Int)
}