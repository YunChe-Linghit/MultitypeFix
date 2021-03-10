package oms.mmc.fast.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import oms.mmc.fast.vm.model.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * <b>Project:</b> xiuxingzhe <br>
 * <b>Create Date:</b> 2020/5/12 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b> 基础的 view model <br>
 */
open class BaseViewModel : ViewModel() {
    var activity: Context? = null
    private val viewModelJob = SupervisorJob()
    protected val uiScope =
        CoroutineScope(Dispatchers.Main + viewModelJob)
    protected val exceptionHandler = CoroutineExceptionHandler { _, e ->
        e.printStackTrace()
    }

    // 运行在UI线程的协程
    fun doUILaunch(
        handler: CoroutineExceptionHandler = exceptionHandler,
        block: suspend CoroutineScope.() -> Unit
    ) {
        uiScope.launch(Dispatchers.Main + handler) {
            block()
        }
    }

    // 运行在IO线程的协程
    suspend fun <T> doIOLaunch(block: suspend CoroutineScope.() -> T): T {
        return withContext(uiScope.coroutineContext + Dispatchers.IO) {
            block()
        }
    }

    // 运行在IO线程的 async 协程
    suspend fun <T> doAsync(block: suspend CoroutineScope.() -> T): Deferred<T?> {
        return uiScope.async(Dispatchers.IO) {
            block()
        }
    }

    suspend fun <T> doIOSuspend(block: suspend CoroutineScope.() -> T) =
        suspendCoroutine<Response<T>> {
            uiScope.launch(Dispatchers.IO) {
                try {
                    it.resume(Response(block(), null))
                } catch (e: Throwable) {
                    e.printStackTrace()
                    it.resume(Response<T>(null, e))
                }
            }
        }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        activity = null
    }
}