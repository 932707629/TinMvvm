package com.soushin.tinmvvm.app.utils

import me.soushin.tinmvvm.config.AppComponent

/**
 * 全局异常监听
 * @auther SouShin
 * @time 2021/6/5 19:15
 */
object CrashHandler {

    fun init(){
        Thread.setDefaultUncaughtExceptionHandler { thread, e ->
//            ALog.e("crash！！！",thread,e)
            AppComponent.rxErrorHandler?.mHandlerFactory?.handleError(e)
        }
    }



}