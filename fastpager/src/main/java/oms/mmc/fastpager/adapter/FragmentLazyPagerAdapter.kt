package oms.mmc.fastpager.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * <b>Create Date:</b> 2/4/21 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b> 支持懒加载的FragmentLazyPagerAdapter <br>
 */
open class FragmentLazyPagerAdapter(
    fragmentManager: FragmentManager,
    protected val fragments: MutableList<Fragment>,
    protected val titles: MutableList<String>
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int) = fragments[position]

    override fun getCount() = fragments.size

    override fun getPageTitle(position: Int) = titles[position]

}