package oms.mmc.fastlist.view

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import oms.mmc.fast.base.ext.dp
import oms.mmc.fast.base.ext.sp
import oms.mmc.fastlist.R
import oms.mmc.fastlist.util.ClickUtils
import oms.mmc.fastlist.util.ScreenUtils
import oms.mmc.fastlist.util.StatusBarUtil

/**
 * <b>Create Date:</b> 2020/9/3 <br>
 * <b>@author:</b> yunche <br>
 * <b>Address:</b> graysonwu2018@gmail.com <br>
 * <b>Description:</b> 顶部标题状态栏视图控件 <br>
 */
class TopBarView(context: Context, attributeSet: AttributeSet?) :
    ConstraintLayout(context, attributeSet) {
    constructor(context: Context) : this(context, null)

    companion object {
        @JvmField
        var gBackResId: Int = 0

        @JvmField
        var gTopBarTitleColor: Int = 0

        @JvmField
        var gTopBarTitleSize: Float = 0f

        @JvmField
        var gTopBarBg: Int = 0
    }

    private var vTopBarStatusHeight: View
    private var vTopBarLeftContainer: LinearLayout
    private var vTopBarTitle: TextView
    private var vTopBarRightContainer: LinearLayout

    private val isNeedStatusBarHeight: Boolean
    private val topBarTitle: String?
    private var topBarTitleColor: Int
    private val topBarTitleSize: Float
    private val isBackVisible: Boolean
    private val backResId: Int
    private var leftContainerViewId: Int
    private var rightContainerViewId: Int

    private var defaultTopBarTitleColor =
        ContextCompat.getColor(getContext(), R.color.fast_list_color_font_black)
    private var defaultTopBarTitleSize = 14f.sp
    private var defaultBackResId = R.drawable.fast_list_back_icon

    init {
        // 从 xml 配置文件中读取参数
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.TopBarView)
        isNeedStatusBarHeight =
            typedArray.getBoolean(R.styleable.TopBarView_needStatusBarHeight, false)
        topBarTitle = typedArray.getString(R.styleable.TopBarView_topBarTitle)
        topBarTitleColor = typedArray.getColor(
            R.styleable.TopBarView_topBarTitleColor,
            0
        )
        topBarTitleSize = typedArray.getDimension(R.styleable.TopBarView_topBarTitleSize, 0f)
        isBackVisible = typedArray.getBoolean(R.styleable.TopBarView_backVisible, true)
        backResId = typedArray.getResourceId(
            R.styleable.TopBarView_backResId,
            0
        )
        leftContainerViewId =
            typedArray.getResourceId(R.styleable.TopBarView_leftContainerViewId, -1)
        rightContainerViewId =
            typedArray.getResourceId(R.styleable.TopBarView_rightContainerViewId, -1)
        typedArray.recycle()
        // 加载布局
        LayoutInflater.from(context).inflate(R.layout.fast_list_top_bar_view, this, true)
        vTopBarStatusHeight = findViewById(R.id.vTopBarStatusHeight)
        vTopBarLeftContainer = findViewById(R.id.vTopBarLeftContainer)
        vTopBarTitle = findViewById(R.id.vTopBarTitle)
        vTopBarRightContainer = findViewById(R.id.vTopBarRightContainer)
        // 创建视图
        setupView()
    }

    private fun setupView() {
        // 状态栏高度
        if (isNeedStatusBarHeight) {
            val params = vTopBarStatusHeight.layoutParams
            params.height = StatusBarUtil.getStatusBarHeight(context)
            vTopBarStatusHeight.layoutParams = params
            vTopBarStatusHeight.visibility = View.VISIBLE
        } else {
            vTopBarStatusHeight.visibility = View.GONE
        }
        // 背景
        if (gTopBarBg != 0) {
            setBackgroundResource(gTopBarBg)
        }
        // 标题
        if (!topBarTitle.isNullOrEmpty()) {
            setTitle(topBarTitle)
        }
        // 标题颜色
        topBarTitleColor = when {
            topBarTitleColor != 0 -> {
                topBarTitleColor
            }
            gTopBarTitleColor != 0 -> {
                gTopBarTitleColor
            }
            else -> {
                defaultTopBarTitleColor
            }
        }
        setTitleColor(topBarTitleColor)
        // 标题字体大小
        val titleSize = when {
            topBarTitleSize != 0f -> {
                topBarTitleSize
            }
            gTopBarTitleSize != 0f -> {
                gTopBarTitleSize
            }
            else -> {
                defaultTopBarTitleSize
            }
        }
        setTitleSize(titleSize)
        // 左边的按钮容器
        if (leftContainerViewId == -1) {
            // 返回键
            if (isBackVisible) {
                // 优先使用设置的，其次使用全局的，最后使用默认的
                val resId = when {
                    backResId != 0 -> {
                        backResId
                    }
                    gBackResId != 0 -> {
                        gBackResId
                    }
                    else -> {
                        defaultBackResId
                    }
                }
                generateImageView(vTopBarLeftContainer, resId) {
                    if (it.context != null && it.context is Activity) {
                        (it.context as Activity).finish()
                    }
                }
            }
        } else {
            LayoutInflater.from(context).inflate(leftContainerViewId, vTopBarLeftContainer, true)
        }
        // 右边的按钮容器
        if (rightContainerViewId != -1) {
            LayoutInflater.from(context).inflate(rightContainerViewId, vTopBarRightContainer, true)
        }
    }

    /**
     * 生成 ImageView 对象，并且添加到对应的容器
     */
    private fun generateImageView(root: ViewGroup, resId: Int, listener: OnClickListener) {
        val vTopBarBtn = View.inflate(
            context,
            R.layout.fast_list_top_bar_btn_image_view,
            null
        ) as ImageView
        val layoutParams = LinearLayout.LayoutParams(
            ScreenUtils.dp2px(context, 48f),
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        vTopBarBtn.setImageResource(resId)
        ClickUtils.applySingleDebouncing(vTopBarBtn, listener)
        root.addView(vTopBarBtn, layoutParams)
    }

    /**
     * 生成 TextView 对象，并且添加到对应的容器
     */
    private fun generateTextView(root: ViewGroup, text: String, listener: OnClickListener) {
        val vTopBarBtn = View.inflate(
            context,
            R.layout.fast_list_top_bar_btn_text_view,
            null
        ) as TextView
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        vTopBarBtn.setPadding(15.dp, 0, 15.dp, 0)
        vTopBarBtn.setTextColor(topBarTitleColor)
        vTopBarBtn.text = text
        ClickUtils.applySingleDebouncing(vTopBarBtn, listener)
        root.addView(vTopBarBtn, layoutParams)
    }

    /**
     * 根据数据加载对应容器布局
     */
    private fun containerInflater(containerBtnData: MutableList<MutableList<*>>, root: ViewGroup) {
        containerBtnData.forEach { btnParam ->
            if (btnParam.size >= 2) {
                var resId: Int? = null
                var text: String? = null
                var clickListener: OnClickListener? = null
                if (btnParam[0] is Int) {
                    resId = btnParam[0] as Int
                } else if (btnParam[0] is String) {
                    text = btnParam[0] as String
                }
                if (btnParam[1] is OnClickListener) {
                    clickListener = btnParam[1] as OnClickListener
                }
                if (resId != null && clickListener != null) {
                    generateImageView(root, resId, clickListener)
                } else if (text != null && clickListener != null) {
                    generateTextView(root, text, clickListener)
                }
            }
        }
    }

    /**
     * 设置标题
     */
    fun setTitle(resId: Int) {
        vTopBarTitle.setText(resId)
    }

    /**
     * 设置标题
     */
    fun setTitle(title: String) {
        vTopBarTitle.text = title
    }

    /**
     * 设置标题颜色
     */
    fun setTitleColor(titleColor: Int) {
        vTopBarTitle.setTextColor(titleColor)
    }

    /**
     * 设置标题字体大小
     */
    fun setTitleSize(titleSize: Float) {
        vTopBarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize)
    }

    /**
     * 获取左边的容器
     */
    fun getLeftContainer(): LinearLayout {
        return vTopBarLeftContainer
    }

    fun setBackIconVisibility(visibility: Int) {
        val vTopBarBackIcon = findViewById<ImageView>(R.id.vTopBarBackIcon)
        if (vTopBarBackIcon != null) {
            vTopBarBackIcon.visibility = visibility
        }
    }

    /**
     * 获取右边的容器
     */
    fun getRightContainer(): LinearLayout {
        return vTopBarRightContainer
    }

    /**
     * 设置左边容器按钮，已设置 leftContainerViewId 后无效
     * 格式： [[int, View.OnClickListener], ...]
     * 说明： 第一个参数为图片资源，第二个参数为点击事件
     */
    fun addLeftContainerItem(leftContainerBtnData: MutableList<MutableList<*>>) {
        if (leftContainerViewId == -1) {
            containerInflater(leftContainerBtnData, vTopBarLeftContainer)
        }
    }

    /**
     * 设置右边容器按钮，已设置 leftContainerViewId 后无效
     * 格式： [[int, View.OnClickListener], ...]
     * 说明： 第一个参数为图片资源，第二个参数为点击事件
     */
    fun addRightContainerItem(rightContainerBtnData: MutableList<MutableList<*>>) {
        if (rightContainerViewId == -1) {
            containerInflater(rightContainerBtnData, vTopBarRightContainer)
        }
    }
}