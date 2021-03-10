package oms.mmc.multitypefix.fragment

import oms.mmc.fastpager.FastPager
import oms.mmc.fastpager.base.BaseFastPagerFragment
import oms.mmc.fastpager.config.FastPagerConfig
import oms.mmc.multitypefix.R

/**
 * <b>Create Date:</b> 1/4/21 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b>  <br>
 */
class TwelveFragment : BaseFastPagerFragment() {
    override fun onItemSet(list: MutableList<FastPager>) {
        list.add(FastPager(EightOneFragment(), "唐砖", 1))
        list.add(FastPager(ElevenFragment(), "庆余年", 2))
        list.add(FastPager(EightOneFragment(), "双世宠妻", 3))
    }

    override fun moreConfig(config: FastPagerConfig) {
        config.layoutId = R.layout.fragment_ten
    }
}