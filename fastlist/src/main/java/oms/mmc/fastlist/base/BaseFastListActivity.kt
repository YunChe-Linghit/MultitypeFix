package oms.mmc.fastlist.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.yokeyword.fragmentation.SupportActivity
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
 * <b>Description:</b> 快速列表基础 Activity <br>
 */
abstract class BaseFastListActivity : SupportActivity() {

    private var config: FastListConfig? = null
    private var vFastListView: FastListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = LayoutInflater.from(this).inflate(R.layout.fast_list_layout, null, false)
        setContentView(view)
        initView()
        setData()
    }

    private fun initView() {
        // 获取绑定的 ViewModel
        val viewModel = bindViewModel()
        config = FastListConfig(this)
        config?.itemRegisterListener = OnItemRegisterListener(::onItemRegister)
        config?.enableLoadMore = true
        config?.onLoadDataListener = OnLoadDataListener(viewModel::onLoadData)
        moreConfig(config!!)
        vFastListView = findViewById(R.id.vFastListView)
        if (vFastListView == null) return
        vFastListView?.initView(config!!)
        // 绑定 ViewModel 和 FastListView
        viewModel.list = vFastListView!!.items
        viewModel.handleDataCallback = { list, pageSize ->
            vFastListView?.handleData(list, pageSize)
        }
        viewModel.handleErrorCallback = {
            vFastListView?.handleError()
        }
        viewModel.notifyDataSetChangedCallback = { list ->
            vFastListView?.notifyDataSetChanged(list)
        }
        // 开始请求数据
        vFastListView?.onRequest()
    }

    abstract fun bindViewModel(): BaseFastViewModel

    abstract fun onItemRegister(adapter: RAdapter)

    protected open fun moreConfig(config: FastListConfig) {

    }

    protected open fun setData() {

    }

    override fun onDestroy() {
        super.onDestroy()
        if (config == null) return
        // 全局参数的 View 不能复用，必须在销毁时从父视图中移除
        removeFromParent(config!!.refreshHeader.view)
        removeFromParent(config!!.refreshFooter.view)
    }

    private fun removeFromParent(view: View) {
        val parent = view.parent
        if (parent is ViewGroup) {
            parent.removeView(view)
        }
    }
}