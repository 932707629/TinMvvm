package com.soushin.tinmvvm.config

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.hjq.toast.ToastUtils
import com.soushin.tinmvvm.utils.CrashHandler
import com.soushin.tinmvvm.widget.ToastStyle
import me.soushin.tinmvvm.listener.AppLifecycle

/**
 * 功能相当于application
 * @auther SouShin
 * @time 2020/7/15 15:40
 */
class AppLifecycleImpl :AppLifecycle {

    override fun attachBaseContext(base: Context) {
        MultiDex.install(base)
    }

    override fun onCreate(application: Application) {
        ToastUtils.init(application, ToastStyle())
        //异常监听
        CrashHandler.init()
    }

    override fun onTerminate(application: Application) {

    }


}