package oms.mmc.fastlist.listener

import oms.mmc.fast.multitype.RAdapter

/**
 * <b>Create Date:</b> 2020/9/7 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b> 列表项注册监听 <br>
 */
fun interface OnItemRegisterListener {
    fun onItemRegister(adapter: RAdapter)
}