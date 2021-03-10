package oms.mmc.multitypefix.fragment

import oms.mmc.fast.base.BaseFastFragment
import oms.mmc.fast.base.ext.setOnClickDebouncing
import oms.mmc.multitypefix.databinding.FragmentEightOneBinding

/**
 * <b>Create Date:</b> 2/4/21 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b>  <br>
 */
class EightOneFragment : BaseFastFragment<FragmentEightOneBinding>() {

    override fun setupViewBinding(): FragmentEightOneBinding {
        return FragmentEightOneBinding.inflate(layoutInflater)
    }

    override fun initView() {
        viewBinding.vText.setOnClickDebouncing {
            viewBinding.vText.text = "哈哈"
        }
    }

    override fun isNeedLazy(): Boolean {
        return false
    }
}