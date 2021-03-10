package oms.mmc.fastlist.dialog

import android.widget.TextView
import oms.mmc.fastlist.R
import oms.mmc.fastlist.dialog.base.BaseDialog

/**
 * <b>Create Date:</b> 2020/9/5 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b> 等待弹窗 <br>
 */
class LoadingDialog : BaseDialog() {
    private lateinit var vLoadingContent: TextView

    override fun onInflaterViewId(): Int {
        return R.layout.fast_list_dialog_loading
    }

    override fun convertView(holder: ViewHolder, dialog: BaseDialog) {
        vLoadingContent = holder.getView(R.id.vLoadingContent)
        isCancelable = false
    }

    override fun initData() {
        setSize(200, 0)
    }

    fun setContent(content: String): LoadingDialog {
        vLoadingContent.text = content
        return this
    }

    fun setContent(resId: Int): LoadingDialog {
        vLoadingContent.setText(resId)
        return this
    }
}