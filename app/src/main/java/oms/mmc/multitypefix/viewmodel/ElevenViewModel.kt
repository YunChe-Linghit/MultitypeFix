package oms.mmc.multitypefix.viewmodel

import com.scwang.smartrefresh.layout.api.RefreshLayout
import oms.mmc.fastlist.viewmodel.BaseFastViewModel

/**
 * <b>Create Date:</b> 1/4/21 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b>  <br>
 */
class ElevenViewModel : BaseFastViewModel() {
    override fun onLoadData(recyclerView: RefreshLayout, currentPage: Int) {
        val list = mutableListOf("哈哈哈", 123, "哈哈哈", 123, "哈哈哈", 123, "哈哈哈", 123, "哈哈哈", 123, "哈哈哈", 123, "哈哈哈", 123 )
        handleData(list, 20)
    }
}