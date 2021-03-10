package oms.mmc.fast.vm.model

/**
 * <b>Create Date:</b> 2020/8/4 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b> 响应体 <br>
 */
class Response<T>(var data: T?, var throwable: Throwable?) {

    fun isSuccessful(): Boolean {
        return throwable == null
    }

}