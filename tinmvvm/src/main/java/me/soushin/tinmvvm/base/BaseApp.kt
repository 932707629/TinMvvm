package me.soushin.tinmvvm.base

import android.app.Application
import android.content.Context
import me.soushin.tinmvvm.config.AppDelegate

/**
 *
 * @auther SouShin
 * @time 2020/7/15 12:46
 */
open class BaseApp : Application() {
    var appLifecycle:AppDelegate?=null

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        if (appLifecycle==null){
            appLifecycle=AppDelegate(base)
        }
        appLifecycle?.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        instance=this
        appLifecycle?.onCreate(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        appLifecycle?.onTerminate(this)
    }

    companion object {
        //提供一个application实例供外部调用
        var instance :BaseApp ?=null
    }

}