package com.soushin.tinmvvm.app.widget

import android.app.Dialog
import android.content.Context
import androidx.fragment.app.Fragment
import razerdp.basepopup.BasePopupWindow

/**
 * 弹窗基础类
 * @auther SouShin
 * @time 2019/10/30 10:14
 */
abstract class BasePopup : BasePopupWindow {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, width: Int, height: Int) : super(context, width, height)
    constructor(fragment: Fragment?) : super(fragment)
    constructor(fragment: Fragment?, width: Int, height: Int) : super(fragment, width, height)
    constructor(dialog: Dialog?) : super(dialog)
    constructor(dialog: Dialog?, width: Int, height: Int) : super(dialog, width, height)


}