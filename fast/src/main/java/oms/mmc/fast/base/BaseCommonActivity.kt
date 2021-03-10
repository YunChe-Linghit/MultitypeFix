package oms.mmc.fast.base

import android.content.Intent
import me.yokeyword.fragmentation.SupportFragment
import oms.mmc.fast.R
import oms.mmc.fast.databinding.BaseContainerBinding

/**
 * <b>Create Date:</b> 2020/7/24 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b> 快应用框架-activity 基础容器 <br>
 */
abstract class BaseCommonActivity : BaseFastActivity<BaseContainerBinding>() {
    private var mFragment: SupportFragment? = null

    override fun setupViewBinding(): BaseContainerBinding {
        return BaseContainerBinding.inflate(layoutInflater)
    }

    override fun initView() {
        //加入参数
        val fragmentClazz = getFragmentClass()
        val fragment = fragmentClazz.newInstance()
        if (fragment != null && fragment is SupportFragment) {
            mFragment = fragment
            fragment.arguments = intent.extras
            loadRootFragment(R.id.base_container, fragment)
        }
    }

    abstract fun getFragmentClass(): Class<*>

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mFragment?.onActivityResult(requestCode, resultCode, data)
    }
}