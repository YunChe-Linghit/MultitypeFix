package oms.mmc.fastpager.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import oms.mmc.fastpager.FastPager

/**
 * <b>Create Date:</b> 12/23/20 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b> Pager 适配器 <br>
 */
open class FastPagerAdapter(
    fragmentActivity: FragmentActivity,
    protected val list: MutableList<FastPager>
) :
    FragmentStateAdapter(fragmentActivity) {
    // 工作集合是否可变标记，默认不可变，添加的集合 index 不等于-1时可变
    private var changeable = false

    init {
        changeable = list.firstOrNull()?.index != -1L
    }

    override fun createFragment(position: Int): Fragment {
        return list[position].fragment
    }

    override fun getItemCount(): Int {
        return list.size
    }

    /**
     * ViewPager2 实现动态修改 Fragment 时重写，index 是用于判断的对象唯一的标识
     */
    override fun getItemId(position: Int): Long {
        return if (changeable) {
            list[position].index
        } else {
            super.getItemId(position)
        }
    }

    /**
     * ViewPager2 实现动态修改 Fragment 时重写，需要同时重写 {@link #getItemId(int)}
     * 默认不重写该方法，工作集合不能添加、删除和修改
     */
    override fun containsItem(itemId: Long): Boolean {
        return if (changeable) {
            list.find {
                it.index == itemId
            } != null
        } else {
            super.containsItem(itemId)
        }
    }
}