package com.soushin.tinmvvm.widget

import android.content.Context
import android.view.View
import razerdp.basepopup.BasePopupWindow

/**
 * 弹窗基础类
 * @auther SouShin
 * @time 2019/10/30 10:14
 */
abstract class BasePopup(context: Context?) : BasePopupWindow(context) {

    override fun onCreateContentView(): View {
        return createPopupById(getLayoutId())
    }

    abstract fun getLayoutId():Int

}