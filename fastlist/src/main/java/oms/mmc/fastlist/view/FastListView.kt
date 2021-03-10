package oms.mmc.fastlist.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import oms.mmc.fast.multitype.RAdapter
import oms.mmc.fastlist.R
import oms.mmc.fastlist.config.FastListConfig

/**
 * <b>Create Date:</b> 2020/9/5 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b> 快速列表控件 <br>
 */
class FastListView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {
    constructor(context: Context) : this(context, null)

    private var vTopBarView: TopBarView? = null
    private var vRecyclerView: RecyclerView? = null
    private var vSmartRefresh: SmartRefreshLayout? = null
    private var vStatusView: StatusView? = null

    private var config: FastListConfig? = null

    private var adapter: RAdapter = RAdapter()
    var items: MutableList<Any> = mutableListOf()

    private var lastReqTime = 0L
    private var interval = 0
    private var currentPage = FIRST_PAGE
    private var isNoMoreData = false

    companion object {
        const val FIRST_PAGE = 1
    }

    fun initView(config: FastListConfig) {
        // 加载布局 xml 配置文件
        LayoutInflater.from(context).inflate(config.layoutId, this, true)
        vTopBarView = findViewById(R.id.vTopBarView)
        vRecyclerView = findViewById(R.id.vRecyclerView)
        vSmartRefresh = findViewById(R.id.vSmartRefresh)
        vStatusView = findViewById(R.id.vStatusView)
        if (vTopBarView == null || vRecyclerView == null || vSmartRefresh == null || vStatusView == null) return
        // 设置配置
        this.config = config
        vTopBarView?.visibility = if (config.isHideTopBar) {
            View.GONE
        } else {
            View.VISIBLE
        }
        // 顶部状态栏视图
        if (config.titleVisible.not()) {
            vTopBarView?.visibility = View.GONE
        }
        config.title?.let {
            vTopBarView?.setTitle(it)
        }
        config.titleColor?.let {
            vTopBarView?.setTitleColor(it)
        }
        config.titleSize?.let {
            vTopBarView?.setTitleSize(it)
        }
        config.bgResId?.let {
            vTopBarView?.setBackgroundResource(it)
        }
        config.backIconVisible?.let {
            vTopBarView?.setBackIconVisibility(it)
        }
        config.leftContainerBtnData?.let {
            vTopBarView?.addLeftContainerItem(it)
        }
        config.rightContainerBtnData?.let {
            vTopBarView?.addRightContainerItem(it)
        }
        // 创建列表视图
        config.itemRegisterListener?.onItemRegister(adapter)
        adapter.items = items
        vRecyclerView?.adapter = adapter
        vRecyclerView?.layoutManager = config.layoutManager
        // 创建刷新控件
        interval = config.interval
        vSmartRefresh?.setEnableRefresh(config.enableRefresh)
        vSmartRefresh?.setRefreshHeader(config.refreshHeader)
        vSmartRefresh?.setEnableLoadMore(config.enableLoadMore)
        vSmartRefresh?.setRefreshFooter(config.refreshFooter)
        vSmartRefresh?.setOnRefreshListener(::onRefresh)
        vSmartRefresh?.setOnLoadMoreListener(::onLoadMore)
    }

    fun onRequest() {
        vStatusView?.setStatus(StatusView.LOADING_STATUS)
        onRefresh()
    }

    /**
     * 刷新数据回调
     */
    private fun onRefresh(refreshLayout: RefreshLayout? = null) {
        val current = System.currentTimeMillis()
        if (current - lastReqTime > interval) {
            loadData()
        } else {
            // 在拦截时间内，做假刷新操作
            vSmartRefresh?.finishRefresh(1200)
        }
    }

    fun loadData() {
        if (vSmartRefresh == null) return
        // 重置当前页码
        currentPage = FIRST_PAGE
        // 重置加载完毕标识
        isNoMoreData = false
        // 加载数据
        config?.onLoadDataListener?.onLoadData(vSmartRefresh!!, currentPage)
    }

    /**
     * 加载更多数据回调
     */
    private fun onLoadMore(refreshLayout: RefreshLayout) {
        if (isNoMoreData) {
            refreshLayout.finishLoadMoreWithNoMoreData()
            return
        }
        loadMore()
    }

    fun loadMore() {
        if (vSmartRefresh == null) return
        if (isNoMoreData) {
            return
        }
        // 加载更多，页码加一
        currentPage++
        config?.onLoadDataListener?.onLoadData(vSmartRefresh!!, currentPage)
    }

    /**
     * 处理加载数据成功的情况
     * @data 数据集合
     * @pageSize 每页数据量，默认一页10个，若不为10，请传该参数
     */
    fun handleData(data: List<Any>?, pageSize: Int = 10) {
        // 结束刷新状态
        vSmartRefresh?.finishRefresh()
        if (data == null || data.isEmpty()) {
            if (isFirstPage()) {
                if (items.isEmpty()) {
                    vStatusView?.setStatus(StatusView.EMPTY_STATUS)
                }
            } else {
                // 最后一页数据为空，页码减一（更多的时候加一了），设置为没有更多数据状态
                currentPage--
                vSmartRefresh?.finishLoadMoreWithNoMoreData()
            }
        } else {
            if (isFirstPage()) {
                items.clear()
                items.addAll(data)
                // 设置刷新的最后一个请求时间，用于假刷新
                lastReqTime = System.currentTimeMillis()
                adapter.notifyDataSetChanged()
                vStatusView?.setStatus(StatusView.HAS_DATA_STATUS)
            } else {
                val list = mutableListOf<Any>()
                list.addAll(items)
                list.addAll(data)
                notifyDataSetChanged(list)
            }
            // 当已设置分页大小时，用分页大小判断是否已经是最后一页
            if (pageSize > 0) {
                isNoMoreData = data.size < pageSize
            }
            if (!isNoMoreData) {
                // 结束加载更多状态
                vSmartRefresh?.finishLoadMore(true)
            } else {
                // 结束加载更多状态，并设置为没有更多数据状态
                vSmartRefresh?.finishLoadMoreWithNoMoreData()
            }
        }
    }

    /**
     * 处理加载数据失败的情况
     */
    fun handleError() {
        // 结束刷新状态
        vSmartRefresh?.finishRefresh()
        if (isFirstPage()) {
            if (items.isEmpty()) {
                //第一次的时候才需要显示error
                vStatusView?.setStatus(StatusView.NETWORK_ERROR_STATUS) {
                    // 点击了刷新
                    vStatusView?.setStatus(StatusView.LOADING_STATUS)
                    onRefresh()
                }
            }
        } else {
            // 加载更多数据失败，页码减一（更多的时候加一了），结束加载更多状态
            currentPage--
            vSmartRefresh?.finishLoadMore(false)
        }
    }

    /**
     * 刷新所有数据源
     */
    fun notifyDataSetChanged(list: List<Any>) {
        items.clear()
        items.addAll(list)
        adapter.notifyDataSetChanged()
    }

    /**
     * 列表加载的数据是否为第一页
     */
    fun isFirstPage(): Boolean {
        return currentPage == FIRST_PAGE
    }
}