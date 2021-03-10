package oms.mmc.fastlist.listener

import com.scwang.smartrefresh.layout.api.RefreshLayout

/**
 * <b>Create Date:</b> 2020/9/7 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b> 刷新接口 <br>
 */
fun interface OnLoadDataListener {
    fun onLoadData(recyclerView: RefreshLayout, currentPage: Int)
}