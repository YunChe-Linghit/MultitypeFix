package oms.mmc.fastpager.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.yokeyword.fragmentation.SupportFragment
import oms.mmc.fastpager.FastPager
import oms.mmc.fastpager.R
import oms.mmc.fastpager.config.FastPagerConfig
import oms.mmc.fastpager.listener.OnItemSetListener
import oms.mmc.fastpager.view.FastPagerView

/**
 * <b>Create Date:</b> 12/24/20 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b>  <br>
 */
abstract class BaseFastPagerFragment : SupportFragment() {
    private var vFastPagerView: FastPagerView? = null

    private var isLoaded = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fast_pager_layout, container, false)
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
        if (activity == null) return
        val config = FastPagerConfig(activity!!)
        config.itemSetListener = OnItemSetListener(::onItemSet)
        moreConfig(config)
        vFastPagerView = view.findViewById(R.id.vFastPagerView)
        vFastPagerView?.initView(config)
    }

    protected abstract fun onItemSet(list: MutableList<FastPager>)

    protected open fun moreConfig(config: FastPagerConfig) {

    }

    protected open fun setData() {

    }

    protected fun getPagerView(): FastPagerView? {
        return vFastPagerView
    }

    override fun onDestroyView() {
        vFastPagerView?.onDestroy()
        super.onDestroyView()
        isLoaded = false
    }
}