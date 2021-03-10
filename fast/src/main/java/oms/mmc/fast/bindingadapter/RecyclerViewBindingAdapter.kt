/*
 * Copyright 2018-2020 KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package oms.mmc.fast.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import oms.mmc.fast.multitype.RAdapter
import oms.mmc.fast.multitype.RAdapter.DiffCallback

/**
 * <b>Create Date:</b> 2020/9/9 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b> rv view binding adapter <br>
 */
object RecyclerViewBindingAdapter {

    @JvmStatic
    @BindingAdapter("adapter")
    fun setAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>?) {
        if (adapter != null) {
            recyclerView.adapter = adapter
        }
    }

    @JvmStatic
    @BindingAdapter("itemDecoration")
    fun setAdapter(recyclerView: RecyclerView, itemDecoration: RecyclerView.ItemDecoration?) {
        if (recyclerView.itemDecorationCount == 0 && itemDecoration != null) {
            recyclerView.addItemDecoration(itemDecoration)
        }
    }

    @JvmStatic
    @BindingAdapter("refreshList", "diffCallback", requireAll = false)
    fun refreshList(recyclerView: RecyclerView, list: List<Any>?, callback: DiffCallback?) {
        if (list != null) {
            val adapter = recyclerView.adapter
            if (adapter is RAdapter) {
                adapter.swapData(list, callback)
            }
        }
    }
}