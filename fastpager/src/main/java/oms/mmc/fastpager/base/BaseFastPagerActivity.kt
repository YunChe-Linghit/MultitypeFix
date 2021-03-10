package oms.mmc.fastpager.base

import android.os.Bundle
import android.view.LayoutInflater
import me.yokeyword.fragmentation.SupportActivity
import oms.mmc.fastpager.FastPager
import oms.mmc.fastpager.R
import oms.mmc.fastpager.config.FastPagerConfig
import oms.mmc.fastpager.listener.OnItemSetListener
import oms.mmc.fastpager.view.FastPagerView

/**
 * <b>Create Date:</b> 12/23/20 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b>  <br>
 */
abstract class BaseFastPagerActivity : SupportActivity() {
    private var vFastPagerView: FastPagerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = LayoutInflater.from(this).inflate(R.layout.fast_pager_layout, null, false)
        setContentView(view)
        initView()
        setData()
    }

    private fun initView() {
        val config = FastPagerConfig(this)
        config.itemSetListener = OnItemSetListener(::onItemSet)
        moreConfig(config)
        vFastPagerView = findViewById(R.id.vFastPagerView)
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

    override fun onDestroy() {
        vFastPagerView?.onDestroy()
        super.onDestroy()
    }
}