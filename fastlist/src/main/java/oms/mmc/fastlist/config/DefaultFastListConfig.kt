package oms.mmc.fastlist.config

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smartrefresh.layout.api.RefreshFooter
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader

/**
 * <b>Create Date:</b> 2020/9/7 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b> 快速列表全局配置 <br>
 */
class DefaultFastListConfig(context: Context?) {
    var enableRefresh: Boolean = true
    var refreshHeader: RefreshHeader = ClassicsHeader(context)
    var enableLoadMore: Boolean = false
    var refreshFooter: RefreshFooter = ClassicsFooter(context)
    var interval: Int? = null
}