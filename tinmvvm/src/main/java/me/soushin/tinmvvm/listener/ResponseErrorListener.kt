package me.soushin.tinmvvm.listener

import android.content.Context

/**
 * 全局错误内容监听回调
 * @author SouShin
 * @time 2021/11/16 14:57
 */
interface ResponseErrorListener {

    fun handleResponseError(context: Context?, t: Throwable?)

    companion object{
        var EMPTY=object :ResponseErrorListener{
            override fun handleResponseError(context: Context?, t: Throwable?) {

            }
        }
    }

}




