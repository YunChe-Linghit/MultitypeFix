package oms.mmc.fastpager.listener

import oms.mmc.fastpager.FastPager

/**
 * <b>Create Date:</b> 12/22/20 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b> 列表项数据监听 <br>
 */
fun interface OnItemSetListener {
    fun onItemSet(list: MutableList<FastPager>)
}