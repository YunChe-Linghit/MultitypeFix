package oms.mmc.fastlist.config

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smartrefresh.layout.api.RefreshFooter
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import oms.mmc.fastlist.R
import oms.mmc.fastlist.listener.OnItemRegisterListener
import oms.mmc.fastlist.listener.OnLoadDataListener

/**
 * <b>Create Date:</b> 2020/9/7 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b> 快速列表配置 <br>
 */
class FastListConfig(context: Context?) {

    companion object {
        // 快速列表控件全局配置
        var defaultConfig: DefaultFastListConfig? = null
    }

    // 页面布局
    @LayoutRes
    var layoutId = R.layout.fast_list_view

    // 列表项注册监听
    var itemRegisterListener: OnItemRegisterListener? = null

    // 布局管理器
    var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)

    // 设置是否允许刷新
    var enableRefresh: Boolean = defaultConfig?.enableRefresh ?: true

    // 刷新控件的头部视图
    var refreshHeader: RefreshHeader = defaultConfig?.refreshHeader ?: ClassicsHeader(context)

    // 设置是否允许下拉加载更多
    var enableLoadMore: Boolean = defaultConfig?.enableLoadMore ?: false

    // 刷新控件的底部视图
    var refreshFooter: RefreshFooter = defaultConfig?.refreshFooter ?: ClassicsFooter(context)

    // 设置数据源的监听器
    var onLoadDataListener: OnLoadDataListener? = null

    // 刷新等待时间（模拟刷新操作）
    var interval: Int = defaultConfig?.interval ?: 3 * 1000

    // 标题是否可见
    var titleVisible: Boolean = true

    // 标题文字
    var title: String? = null

    // 标题颜色
    var titleColor: Int? = null

    // 标题文字大小
    var titleSize: Float? = null

    // 标题布局背景
    var bgResId: Int? = null

    // 返回按钮可见状态
    var backIconVisible: Int? = null

    // 标题布局左容器数据源
    var leftContainerBtnData: MutableList<MutableList<*>>? = null

    // 标题布局右容器数据源
    var rightContainerBtnData: MutableList<MutableList<*>>? = null

    // 控制是否隐藏顶部栏
    var isHideTopBar: Boolean = false
}