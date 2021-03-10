package oms.mmc.fastlist.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.yokeyword.fragmentation.SupportFragment
import oms.mmc.fast.multitype.RAdapter
import oms.mmc.fastlist.R
import oms.mmc.fastlist.config.FastListConfig
import oms.mmc.fastlist.listener.OnItemRegisterListener
import oms.mmc.fastlist.listener.OnLoadDataListener
import oms.mmc.fastlist.view.FastListView
import oms.mmc.fastlist.viewmodel.BaseFastViewModel

/**
 * <b>Create Date:</b> 2020/9/2 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b> 快速列表基础 Fragment <br>
 */
abstract class BaseFastListFragment : SupportFragment() {

    private var config: FastListConfig? = null
    private var vFastListView: FastListView? = null

    private var isLoaded = false
    var isLazyInitFinished: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fast_list_layout, container, false)
        onInitView(view)
        return view
    }

    protected open fun onInitView(view: View) {
        if (!isNeedLazy()) {
            initView(view)
        }
    }

    /**
     * 是否需要懒加载，默认为懒加载
     */
    protected open fun isNeedLazy(): Boolean {
        return false
    }

    override fun onResume() {
        super.onResume()
        if (!isLoaded && !isHidden) {
            lazyInit()
            isLoaded = true
        }
    }

    private fun lazyInit() {
        if (isNeedLazy()) {
            view?.let {
                initView(it)
                setData()
            }
        } else {
            view?.let {
                setData()
            }
        }
    }

    private fun initView(view: View) {
        // 获取绑定的 ViewModel
        val viewModel = bindViewModel()
        config = FastListConfig(context)
        config?.itemRegisterListener = OnItemRegisterListener(::onItemRegister)
        config?.enableLoadMore = true
        config?.onLoadDataListener = OnLoadDataListener(viewModel::onLoadData)
        moreConfig(config!!)
        vFastListView = view.findViewById(R.id.vFastListView)
        if (vFastListView == null) return
        vFastListView?.initView(config!!)
        // 绑定 ViewModel 和 FastListView
        viewModel.context = activity ?: view.context
        viewModel.list = vFastListView!!.items
        viewModel.loadDataCallback = {
            vFastListView?.loadData()
        }
        viewModel.loadMoreCallback = {
            vFastListView?.loadMore()
        }
        viewModel.handleDataCallback = { list, pageSize ->
            vFastListView?.handleData(list, pageSize)
        }
        viewModel.handleErrorCallback = {
            vFastListView?.handleError()
        }
        viewModel.notifyDataSetChangedCallback = { list ->
            vFastListView?.notifyDataSetChanged(list)
        }
        isLazyInitFinished = true
    }

    abstract fun bindViewModel(): BaseFastViewModel

    abstract fun onItemRegister(adapter: RAdapter)

    protected open fun moreConfig(config: FastListConfig) {

    }

    protected open fun setData() {
        // 开始请求数据
        vFastListView?.onRequest()
    }

    protected fun getListView(): FastListView? {
        return vFastListView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isLoaded = false
        // 全局参数的 View 不能复用，必须在销毁时从父视图中移除
        if (isLazyInitFinished) {
            if (config == null) return
            removeFromParent(config!!.refreshHeader.view)
            removeFromParent(config!!.refreshFooter.view)
            isLazyInitFinished = !isLazyInitFinished
        }
    }

    private fun removeFromParent(view: View) {
        val parent = view.parent
        if (parent is ViewGroup) {
            parent.removeView(view)
        }
    }
}