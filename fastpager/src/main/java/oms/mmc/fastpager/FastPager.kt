package oms.mmc.fastpager

import androidx.fragment.app.Fragment

/**
 * <b>Create Date:</b> 12/23/20 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b> 数据源 <br>
 */
data class FastPager(
    var fragment: Fragment,
    // 标签名称
    var tabName: String = "",
    // 唯一的标识，index 不等于-1时，工作集合可变
    var index: Long = -1
)
