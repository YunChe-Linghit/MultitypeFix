package oms.mmc.fast.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import oms.mmc.fast.multitype.RAdapter
import oms.mmc.fast.vm.BaseViewModel

/**
 * <b>Project:</b> xiuxingzhe <br>
 * <b>Create Date:</b> 2020/5/18 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b> rv 基础的 view model <br>
 */
open class BaseRvViewModel : BaseViewModel() {

    val list = MutableLiveData<List<Any>>()

    val diffCallback = ObservableField<RAdapter.DiffCallback>()

    override fun onCleared() {
        super.onCleared()
        list.postValue(null)
        diffCallback.set(null)
    }

}