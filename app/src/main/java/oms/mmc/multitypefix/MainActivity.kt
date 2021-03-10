package oms.mmc.multitypefix

import oms.mmc.fast.base.BaseCommonActivity
import oms.mmc.multitypefix.fragment.NineFragment

class MainActivity : BaseCommonActivity() {

    override fun getFragmentClass(): Class<*> {
        return NineFragment::class.java
    }
}