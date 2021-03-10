package oms.mmc.fastpager.config

import android.graphics.Color
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import oms.mmc.fast.base.ext.sp
import oms.mmc.fastpager.R
import oms.mmc.fastpager.listener.OnItemSetListener
import oms.mmc.fastpager.listener.OnPagerChangeListener
import oms.mmc.fastpager.view.FastPagerView

/**
 * <b>Create Date:</b> 12/22/20 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b> 快速书页配置 <br>
 */
class FastPagerConfig(val activity: FragmentActivity) {

    // 页面布局
    @LayoutRes
    var layoutId = R.layout.fast_pager_view

    // 是否展示标签导航
    var needTabLayout = true

    // 设置布局模式
    var tabMode = TabLayout.MODE_FIXED

    // 设置排版方式
    var tabGravity = TabLayout.GRAVITY_FILL

    // 设置是否铺满指示器
    var isTabIndicatorFullWidth = false

    // 设置指示器颜色
    var selectedTabIndicatorColor = Color.parseColor("#333333")

    // 设置指示器蒙版
    var selectedTabIndicator = R.drawable.fast_tab_indicator

    // 设置点击水波纹效果
    var unboundRipple = false

    // 文字选中颜色
    var activeColor = Color.parseColor("#333333")

    // 文字未选中颜色
    var normalColor = Color.parseColor("#666666")

    // 文字选中大小
    var activeSize = 16f.sp

    // 文字未选中大小
    var normalSize = 14f.sp

    // 列表项添加回调监听器
    var itemSetListener: OnItemSetListener? = null

    // 页面切换回调监听器
    var pagerChangeListener: OnPagerChangeListener? = null

    // 加载模式，默认禁用预加载
    var mode = FastPagerView.DISABLE_PRELOAD_MODE
}