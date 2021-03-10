package oms.mmc.fastpager.listener

/**
 * <b>Create Date:</b> 1/4/21 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b> 页面切换回调监听器 <br>
 */
fun interface OnPagerChangeListener {
    fun onPageChange(position: Int)
}