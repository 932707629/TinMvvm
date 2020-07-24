package me.soushin.tinmvvm.listener

import android.app.Activity

/**
 *
 * @auther SouShin
 * @time 2020/7/21 17:18
 */
interface ExceptionCallBack {

    fun exceptionCallback(errorActivity: Activity, emergency:String)

}