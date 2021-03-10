package oms.mmc.fastlist.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import oms.mmc.fastlist.R
import oms.mmc.fastlist.util.ClickUtils

/**
 * <b>Create Date:</b> 2020/9/3 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b> 状态视图控件 <br>
 */
class StatusView(context: Context, attributeSet: AttributeSet?) :
    LinearLayout(context, attributeSet) {
    constructor(context: Context) : this(context, null)

    private var vStatusViewContent: TextView? = null
    private var vStatusViewImg: ImageView? = null

    companion object {
        const val LOADING_STATUS = 0
        const val EMPTY_STATUS = 1
        const val HAS_DATA_STATUS = 2
        const val NETWORK_ERROR_STATUS = 3
    }

    private var vStatusViewBtn: TextView? = null

    init {
        // 设置对齐方式
        gravity = Gravity.CENTER
        // 设置排列方向
        orientation = VERTICAL
    }

    /**
     * 设置状态
     */
    fun setStatus(status: Int, listener: OnClickListener? = null) {
        if (childCount > 0) {
            removeAllViews()
        }
        when (status) {
            LOADING_STATUS -> {
                if (visibility == View.GONE) {
                    visibility = View.VISIBLE
                }
                // 加载 loading 视图
                LayoutInflater.from(context)
                    .inflate(R.layout.fast_list_loading_status_view, this, true)
                vStatusViewContent = findViewById(R.id.vStatusViewContent)
            }
            EMPTY_STATUS -> {
                if (visibility == View.GONE) {
                    visibility = View.VISIBLE
                }
                // 加载空数据视图
                LayoutInflater.from(context)
                    .inflate(R.layout.fast_list_empty_status_view, this, true)
                vStatusViewImg = findViewById(R.id.vStatusViewImg)
                vStatusViewContent = findViewById(R.id.vStatusViewContent)
            }
            HAS_DATA_STATUS -> {
                if (visibility == View.VISIBLE) {
                    visibility = View.GONE
                }
            }
            NETWORK_ERROR_STATUS -> {
                if (visibility == View.GONE) {
                    visibility = View.VISIBLE
                }
                // 加载空数据视图
                LayoutInflater.from(context)
                    .inflate(R.layout.fast_list_network_error_status_view, this, true)
                vStatusViewImg = findViewById(R.id.vStatusViewImg)
                vStatusViewContent = findViewById(R.id.vStatusViewContent)
                vStatusViewBtn = findViewById(R.id.vStatusViewBtn)
                ClickUtils.applySingleDebouncing(vStatusViewBtn, listener)
            }
        }
    }

    /**
     * 设置内容
     */
    fun setContent(strId: Int) {
        vStatusViewContent?.setText(strId)
    }

    /**
     * 设置内容
     */
    fun setContent(content: String) {
        vStatusViewContent?.text = content
    }

    /**
     * 设置图片
     */
    fun setImage(imgId: Int) {
        vStatusViewImg?.setImageResource(imgId)
    }

    /**
     * 设置按钮文字和点击事件
     */
    fun setBtnClick(strId: Int, listener: OnClickListener? = null) {
        vStatusViewBtn?.let {
            it.setText(strId)
            listener?.let { listener ->
                ClickUtils.applySingleDebouncing(it, listener)
            }
        }
    }

    /**
     * 设置按钮文字和点击事件
     */
    fun setBtnClick(content: String, listener: OnClickListener? = null) {
        vStatusViewBtn?.let {
            it.text = content
            listener?.let { listener ->
                ClickUtils.applySingleDebouncing(it, listener)
            }
        }
    }
}