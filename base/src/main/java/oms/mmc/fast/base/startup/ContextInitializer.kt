package oms.mmc.fast.base.startup

import android.content.Context
import androidx.startup.Initializer
import oms.mmc.fast.base.util.ContextProvider

/**
 * <b>Create Date:</b> 2020/10/10 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b> 全局上下文初始化器 <br>
 */
class ContextInitializer : Initializer<ContextProvider> {
    override fun create(context: Context): ContextProvider {
        ContextProvider.getInstance().context = context
        return ContextProvider.getInstance()
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}