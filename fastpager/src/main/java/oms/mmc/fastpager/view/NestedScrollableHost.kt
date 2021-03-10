package oms.mmc.fastpager.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.absoluteValue
import kotlin.math.sign

/**
 * <b>Create Date:</b> 1/4/21 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b> 外部拦截处理触摸时间的容器 https://developer.android.com/training/animation/vp2-migration#nested-scrollables <br>
 */
open class NestedScrollableHost : ConstraintLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    private var touchSlop = 0
    private var initialX = 0f
    private var initialY = 0f
    private val parentViewPager: ViewPager2?
        get() {
            var v: View? = parent as? View
            while (v != null && v !is ViewPager2) {
                v = v.parent as? View
            }
            return v as? ViewPager2
        }

    private val child: View?
        get() {
            var v: View? = null
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                if (child is ViewPager2) {
                    v = child
                    break
                }
            }
            return v
        }

    init {
        touchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    private fun canChildScroll(orientation: Int, delta: Float): Boolean {
        val direction = -delta.sign.toInt()
        return when (orientation) {
            0 -> child?.canScrollHorizontally(direction) ?: false
            1 -> child?.canScrollVertically(direction) ?: false
            else -> throw IllegalArgumentException()
        }
    }

    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
        handleInterceptTouchEvent(e)
        return super.onInterceptTouchEvent(e)
    }

    private fun handleInterceptTouchEvent(e: MotionEvent) {
        val orientation = parentViewPager?.orientation ?: return

        // 如果子视图不能与父视图朝同一个方向滚动，则提早返回
        if (!canChildScroll(orientation, -1f) && !canChildScroll(orientation, 1f)) {
            return
        }

        if (e.action == MotionEvent.ACTION_DOWN) {
            initialX = e.x
            initialY = e.y
            child ?: return
            val top = child!!.top
            val bottom = child!!.bottom
            if (initialY > top && initialY < bottom) {
                parent.requestDisallowInterceptTouchEvent(true)
            }
        } else if (e.action == MotionEvent.ACTION_MOVE) {
            val dx = e.x - initialX
            val dy = e.y - initialY
            val isVpHorizontal = orientation == ViewPager2.ORIENTATION_HORIZONTAL

            // 假设 ViewPager2 触摸斜率是子视图触摸斜率的两倍
            val scaledDx = dx.absoluteValue * if (isVpHorizontal) .5f else 1f
            val scaledDy = dy.absoluteValue * if (isVpHorizontal) 1f else .5f

            if (scaledDx > touchSlop || scaledDy > touchSlop) {
                if (isVpHorizontal == (scaledDy > scaledDx)) {
                    // 手势是垂直的，允许所有父母拦截
                    parent.requestDisallowInterceptTouchEvent(false)
                } else {
                    // 手势是平行的，询问子视图是否可以朝该方向移动
                    if (canChildScroll(orientation, if (isVpHorizontal) dx else dy)) {
                        // 子视图可以滚动，禁止所有父母拦截
                        parent.requestDisallowInterceptTouchEvent(true)
                    } else {
                        // 子视图无法滚动，允许所有父母拦截
                        parent.requestDisallowInterceptTouchEvent(false)
                    }
                }
            }
        }
    }
}