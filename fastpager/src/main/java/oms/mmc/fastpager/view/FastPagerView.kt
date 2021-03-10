package oms.mmc.fastpager.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import oms.mmc.fastpager.FastPager
import oms.mmc.fastpager.R
import oms.mmc.fastpager.adapter.FastPagerAdapter
import oms.mmc.fastpager.config.FastPagerConfig

/**
 * <b>Create Date:</b> 12/22/20 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b> 快速书页控件 <br>
 */
class FastPagerView(context: Context, attrs: AttributeSet?) : NestedScrollableHost(context, attrs) {

    companion object {
        const val DISABLE_PRELOAD_MODE = 0
        const val PRELOAD_NEXT_MODE = 1
        const val PRELOAD_ALL_MODE = 2
    }

    private var vViewPager: ViewPager2? = null
    private var vTabLayout: TabLayout? = null

    var adapter: FastPagerAdapter? = null
    var items: MutableList<FastPager> = mutableListOf()

    private var changeCallback: OnPageChangeCallback? = null
    private var mediator: TabLayoutMediator? = null

    fun initView(config: FastPagerConfig) {
        // 加载布局 xml 配置文件
        LayoutInflater.from(context).inflate(config.layoutId, this, true)
        vViewPager = findViewById(R.id.vViewPager)
        vTabLayout = findViewById(R.id.vTabLayout)
        if (vViewPager == null || vTabLayout == null) return
        // 去除边界弧形光晕
        val child = vViewPager?.getChildAt(0)
        if (child is RecyclerView) {
            child.overScrollMode = View.OVER_SCROLL_NEVER
        }
        // 设置列表视图数据
        config.itemSetListener?.onItemSet(items)
        adapter = FastPagerAdapter(config.activity, items)
        vViewPager?.adapter = adapter
        // 设置加载模式
        when (config.mode) {
            DISABLE_PRELOAD_MODE -> {
                // 禁用预加载，可见时加载，缓存所有页面
                vViewPager?.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
                if (child is RecyclerView) {
                    child.setItemViewCacheSize(items.size)
                }
            }
            PRELOAD_NEXT_MODE -> {
                // 预加载左右两边各一页的界面，默认缓存三个页面（包含当前）
                vViewPager?.offscreenPageLimit = 1
            }
            PRELOAD_ALL_MODE -> {
                // 所有界面预加载
                vViewPager?.offscreenPageLimit = items.size
            }
        }

        if (config.needTabLayout) {
            vTabLayout?.tabMode = config.tabMode
            vTabLayout?.tabGravity = config.tabGravity
            vTabLayout?.isTabIndicatorFullWidth = config.isTabIndicatorFullWidth
            vTabLayout?.setSelectedTabIndicatorColor(config.selectedTabIndicatorColor)
            vTabLayout?.setSelectedTabIndicator(config.selectedTabIndicator)
            vTabLayout?.setUnboundedRipple(config.unboundRipple)
            // viewPager 页面切换监听
            changeCallback = object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    config.pagerChangeListener?.onPageChange(position)
                    //可以来设置选中时tab的大小
                    val tabCount = vTabLayout?.tabCount ?: 0
                    for (i in 0 until tabCount) {
                        val tab: TabLayout.Tab = vTabLayout?.getTabAt(i) ?: continue
                        val tabView = tab.customView as TextView?
                        tabView?.apply {
                            typeface = if (tab.position == position) {
                                setTextSize(TypedValue.COMPLEX_UNIT_PX, config.activeSize)
                                Typeface.DEFAULT_BOLD
                            } else {
                                setTextSize(TypedValue.COMPLEX_UNIT_PX, config.normalSize)
                                Typeface.DEFAULT
                            }
                        }
                    }
                }
            }
            vViewPager?.registerOnPageChangeCallback(changeCallback!!)

            mediator = TabLayoutMediator(vTabLayout!!, vViewPager!!) { tab, position ->
                // 自定义TabView
                val tabView = TextView(context)
                tabView.gravity = Gravity.CENTER
                val states = arrayOfNulls<IntArray>(2)
                states[0] = intArrayOf(android.R.attr.state_selected)
                states[1] = intArrayOf()
                val colors = intArrayOf(config.activeColor, config.normalColor)
                val colorStateList = ColorStateList(states, colors)
                tabView.text = items[position].tabName
                tabView.setTextSize(TypedValue.COMPLEX_UNIT_PX, config.normalSize)
                tabView.setTextColor(colorStateList)
                tab.customView = tabView
            }
            // 要执行这一句才是真正将两者绑定起来
            mediator?.attach()
        } else {
            vTabLayout?.visibility = View.GONE
            vViewPager?.layoutParams =
                LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        }
    }

    fun getViewPager(): ViewPager2? {
        return vViewPager
    }

    fun onDestroy() {
        mediator?.detach()
        changeCallback?.let {
            vViewPager?.unregisterOnPageChangeCallback(it)
        }
    }
}