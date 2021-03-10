package oms.mmc.multitypefix.fragment

import android.os.Handler
import android.os.Looper
import com.google.android.material.tabs.TabLayout
import oms.mmc.fastpager.FastPager
import oms.mmc.fastpager.base.BaseFastPagerFragment
import oms.mmc.fastpager.config.FastPagerConfig
import oms.mmc.fastpager.view.FastPagerView
import oms.mmc.multitypefix.R

/**
 * <b>Create Date:</b> 12/24/20 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b>  <br>
 */
class NineFragment : BaseFastPagerFragment() {

    override fun isNeedLazy(): Boolean {
        return false
    }

    override fun onItemSet(list: MutableList<FastPager>) {
        list.add(FastPager(EightOneFragment(), "首页", 1))
        list.add(FastPager(EightTwoFragment(), "电影", 2))
        list.add(FastPager(TenFragment(), "电视剧", 3))
        list.add(FastPager(EightTwoFragment(), "动漫", 4))
        list.add(FastPager(EightOneFragment(), "体育", 5))
        list.add(FastPager(EightTwoFragment(), "儿童", 6))
    }

    override fun moreConfig(config: FastPagerConfig) {
        config.needTabLayout = false
        config.tabMode = TabLayout.MODE_SCROLLABLE
        config.selectedTabIndicator = R.drawable.fast_indicator
        config.unboundRipple = true
        config.mode = FastPagerView.PRELOAD_NEXT_MODE
    }

    override fun setData() {
        Handler(Looper.getMainLooper()).postDelayed({
//            // 插入
//            getPagerView().items.add(0, FastPager(EightOneFragment(), "你好，中国", 7))
//            getPagerView().adapter?.notifyItemInserted(0)
//            getPagerView().getViewPager().setCurrentItem(0, false)

//            // 删除
//            getPagerView().items.removeFirst()
//            getPagerView().adapter?.notifyItemRemoved(0)

            // 替换
            getPagerView()!!.items.removeFirst()
            getPagerView()!!.items.add(0, FastPager(EightOneFragment(), "你好，中国", 7))
            getPagerView()!!.adapter?.notifyItemChanged(0)
            getPagerView()!!.getViewPager()?.setCurrentItem(0, false)
        }, 5000)
    }
}