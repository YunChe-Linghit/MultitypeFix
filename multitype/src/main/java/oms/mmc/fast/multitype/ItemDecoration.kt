package oms.mmc.fast.multitype

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import java.math.BigDecimal

/**
 * <b>Create Date:</b> 2020/7/27 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b> LayoutManager 间隔 <br>
 */
class ItemDecoration : RecyclerView.ItemDecoration {
    private var left: Int
    private var top: Int
    private var right: Int
    private var bottom: Int
    private var type: String
    private var spanCount: Int
    private var spacing: Int

    companion object {
        const val SINGLE_TYPE = "single_type"
        const val LINEAR_VERTICAL_TYPE = "linear_vertical_type"
        const val LINEAR_HORIZONTAL_TYPE = "linear_horizontal_type"
        const val GRID_VERTICAL_TYPE = "grid_vertical_type"
    }

    constructor(left: Int, top: Int, right: Int, bottom: Int, type: String = SINGLE_TYPE) {
        this.left = left
        this.top = top
        this.right = right
        this.bottom = bottom
        this.type = type
        this.spanCount = 1
        this.spacing = 0
    }

    constructor(left: Int, top: Int, right: Int, bottom: Int, type: String, spacing: Int) {
        this.left = left
        this.top = top
        this.right = right
        this.bottom = bottom
        this.type = type
        this.spanCount = 1
        this.spacing = spacing
    }

    constructor(
        top: Int,
        bottom: Int,
        type: String = GRID_VERTICAL_TYPE,
        spanCount: Int,
        spacing: Int
    ) {
        this.left = 0
        this.top = top
        this.right = 0
        this.bottom = bottom
        this.type = type
        this.spanCount = spanCount
        this.spacing = spacing
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildLayoutPosition(view)
        val itemCount = parent.adapter?.itemCount ?: 1
        // 四种种样式：
        // 1、单独给每个 Item 设置间距。
        // 2、竖向线性样式，设置整个布局的外边距和每个 Item 之间的垂直间距。
        // 3、横向线性样式，设置整个布局的外边距和每个 Item 之间的水平间距。
        // 4、竖向格子样式，设置整个布局的上下边距和每个 Item 之间的垂直间距，左右边距默认设置无效。
        when (type) {
            SINGLE_TYPE -> {
                outRect.left = left
                outRect.top = top
                outRect.right = right
                outRect.bottom = bottom
            }
            LINEAR_VERTICAL_TYPE -> {
                outRect.left = left
                if (position == 0) {
                    outRect.top = top
                } else {
                    outRect.top = spacing
                }
                outRect.right = right
                if (position == itemCount - 1) {
                    outRect.bottom = bottom
                } else {
                    outRect.bottom = 0
                }
            }
            LINEAR_HORIZONTAL_TYPE -> {
                if (position == 0) {
                    outRect.left = left
                } else {
                    outRect.left = spacing
                }
                outRect.top = top
                if (position == itemCount - 1) {
                    outRect.right = right
                } else {
                    outRect.right = 0
                }
                outRect.bottom = bottom
            }
            GRID_VERTICAL_TYPE -> {
                outRect.left = 0
                if (position - spanCount < 0) {
                    outRect.top = top
                } else {
                    outRect.top = spacing
                }
                outRect.right = 0
                if (BigDecimal((itemCount.toDouble() / spanCount)).setScale(0, BigDecimal.ROUND_UP)
                        .toInt() * spanCount - position <= spanCount
                ) {
                    outRect.bottom = bottom
                } else {
                    outRect.bottom = 0
                }
            }
        }
    }
}