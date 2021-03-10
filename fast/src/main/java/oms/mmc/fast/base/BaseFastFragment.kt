package oms.mmc.fast.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import me.yokeyword.fragmentation.SupportFragment
import oms.mmc.fast.BR
import oms.mmc.fast.databinding.DataBindingConfig

/**
 * <b>Create Date:</b> 2020/7/23 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b> 快应用框架-BaseFastFragment <br>
 */
abstract class BaseFastFragment<T : ViewBinding> : SupportFragment() {
    protected lateinit var fragment: Fragment
    lateinit var viewBinding: T

    private var isLoaded = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragment = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = setupViewBinding()
        val dataBindingConfig = configDataBinding()
        if (dataBindingConfig != null) {
            dataBinding(dataBindingConfig)
        }
        return viewBinding.root
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!isNeedLazy()) {
            initView()
        }
    }

    /**
     * 是否需要懒加载，默认为懒加载
     */
    protected open fun isNeedLazy(): Boolean {
        return true
    }

    override fun onResume() {
        super.onResume()
        if (!isLoaded && !isHidden) {
            lazyInit()
            isLoaded = true
        }
    }

    private fun lazyInit() {
        if (isNeedLazy()) {
            initView()
            setData()
        } else {
            setData()
        }
    }

    protected abstract fun initView()

    protected open fun setData() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        isLoaded = false
    }
}