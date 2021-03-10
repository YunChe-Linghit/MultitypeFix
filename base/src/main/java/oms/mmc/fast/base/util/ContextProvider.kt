package oms.mmc.fast.base.util

import android.content.Context

/**
 * <b>Create Date:</b> 2020/10/10 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b>  <br>
 */
class ContextProvider {

    lateinit var context: Context

    companion object {
        private val instance = ContextProvider()

        @JvmStatic
        fun getInstance(): ContextProvider {
            return instance
        }
    }

}