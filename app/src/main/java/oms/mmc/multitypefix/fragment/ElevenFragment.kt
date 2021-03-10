package oms.mmc.multitypefix.fragment

import android.widget.Toast
import androidx.fragment.app.viewModels
import oms.mmc.fast.multitype.RAdapter
import oms.mmc.fastlist.base.BaseFastListFragment
import oms.mmc.fastlist.config.FastListConfig
import oms.mmc.fastlist.viewmodel.BaseFastViewModel
import oms.mmc.multitypefix.R
import oms.mmc.multitypefix.viewbinder.ExampleViewBinder
import oms.mmc.multitypefix.viewbinder.ExampleViewBinder2
import oms.mmc.multitypefix.viewmodel.ElevenViewModel

/**
 * <b>Create Date:</b> 1/4/21 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b>  <br>
 */
class ElevenFragment : BaseFastListFragment() {
    private val viewModel: ElevenViewModel by viewModels()

    override fun bindViewModel(): BaseFastViewModel {
        return viewModel
    }

    override fun onItemRegister(adapter: RAdapter) {
        adapter.register(ExampleViewBinder {
            Toast.makeText(context, "文本点击", Toast.LENGTH_SHORT).show()
        })
        adapter.register(ExampleViewBinder2 {
            Toast.makeText(context, "图片点击", Toast.LENGTH_SHORT).show()
        })
    }

    override fun moreConfig(config: FastListConfig) {
        config.layoutId = R.layout.fragment_eleven
    }
}