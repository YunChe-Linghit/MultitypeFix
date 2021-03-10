package oms.mmc.fast.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.viewbinding.ViewBinding
import me.yokeyword.fragmentation.SupportActivity
import oms.mmc.fast.BR
import oms.mmc.fast.databinding.DataBindingConfig

/**
 * <b>Create Date:</b> 2020/7/23 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b> 快应用框架-BaseFastActivity <br>
 */
abstract class BaseFastActivity<T : ViewBinding> : SupportActivity() {
    protected lateinit var activity: AppCompatActivity
    protected lateinit var context: Context
    protected lateinit var viewBinding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = this
        context = baseContext

        viewBinding = setupViewBinding()
        val dataBindingConfig = configDataBinding()
        if (dataBindingConfig != null) {
            dataBinding(dataBindingConfig)
        }
        setContentView(viewBinding.root)

        initView()
        setData()
    }

    protected abstract fun setupViewBinding(): T

    protected open fun configDataBinding(): DataBindingConfig? {
        return null
    }

    private fun dataBinding(dataBindingConfig: DataBindingConfig) {
        val binding = DataBindingUtil.bind<ViewDataBinding>(viewBinding.root) as ViewDataBinding
        binding.lifecycleOwner = this
        binding.setVariable(BR.vm, dataBindingConfig.viewModel)
        dataBindingConfig.adapter?.let {
            binding.setVariable(BR.adapter, it)
        }
        dataBindingConfig.itemDecoration?.let {
            binding.setVariable(BR.itemDecoration, it)
        }
        val bindingParams = dataBindingConfig.getBindingParams()
        var i = 0
        val length = bindingParams.size()
        while (i < length) {
            binding.setVariable(bindingParams.keyAt(i), bindingParams.valueAt(i))
            i++
        }
    }

    protected abstract fun initView()

    protected open fun setData() {

    }

}