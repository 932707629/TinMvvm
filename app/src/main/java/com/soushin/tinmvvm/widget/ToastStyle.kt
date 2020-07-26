package com.soushin.tinmvvm.widget

import android.view.Gravity
import com.hjq.toast.IToastStyle

/**
 * 土司样式
 * @author created by Soushin
 * @time 2020/1/7 13 20
 */
class ToastStyle :IToastStyle{

    override fun getGravity(): Int {
        return Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
    }

    override fun getXOffset(): Int {
        return 0
    }

    override fun getYOffset(): Int {
        return 240
    }

    override fun getZ(): Int {
        return 30
    }

    override fun getCornerRadius(): Int {
        return 5
    }

    override fun getBackgroundColor(): Int {
        return -0x11a8a8a9
    }

    override fun getTextColor(): Int {
        return -0x1
    }

    override fun getTextSize(): Float {
        return 14f
    }

    override fun getMaxLines(): Int {
        return 3
    }

    override fun getPaddingStart(): Int {
        return 20
    }

    override fun getPaddingTop(): Int {
        return 8
    }

    override fun getPaddingEnd(): Int {
        return paddingStart
    }

    override fun getPaddingBottom(): Int {
        return paddingTop
    }


}