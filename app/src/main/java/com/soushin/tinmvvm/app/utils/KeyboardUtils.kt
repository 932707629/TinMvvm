package com.soushin.tinmvvm.app.utils

import android.app.Activity
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


/**
 *
 * @auther SouShin
 * @time 2020/7/13 11:13
 */
object KeyboardUtils {

//--------------------------处理EditText显示隐藏输入法-------------------------------

    //--------------------------处理EditText显示隐藏输入法-------------------------------
    /**
     * 处理EditText显示隐藏输入法
     * 点击EditText显示输入法  点击其他View隐藏输入法
     * @param activity
     * @param event
     */
    fun dispatchEditText(activity: Activity, event: MotionEvent?) {
        val v: View? = activity.currentFocus
        if(v!=null&&event!=null){
            if (event.action == MotionEvent.ACTION_DOWN && isShouldHideKeyboard(
                    v,
                    event
                )
            ){
                hideSoftInput(v)
            }
        }
    }

    /**
     * 是否隐藏软键盘
     *
     * @param v
     * @param event
     * @return
     */
    fun isShouldHideKeyboard(v: View, event: MotionEvent): Boolean {
        if (v is EditText) {
            val l = intArrayOf(0, 0)
            v.getLocationInWindow(l)
            val left = l[0]
            val top = l[1]
            val bottom: Int = top + v.getHeight()
            val right: Int = left + v.getWidth()
            return !(event.x > left && event.x < right && event.y > top && event.y < bottom)
        }
        return false
    }

    /**
     * 隐藏输入法
     *
     * @param v
     */
    fun hideSoftInput(v: View) {
        val imm: InputMethodManager =
            v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive) {
            imm.hideSoftInputFromWindow(v.applicationWindowToken, 0)
        }
    }

    //--------------------------处理EditText显示隐藏输入法-------------------------------
}