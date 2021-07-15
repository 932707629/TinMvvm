package com.soushin.tinmvvm.app

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.hjq.toast.ToastUtils
import com.soushin.tinmvvm.BuildConfig
import com.soushin.tinmvvm.app.utils.CrashHandler
import me.soushin.tinmvvm.config.AppComponent
import me.soushin.tinmvvm.listener.AppLifecycle
import rxhttp.RxHttpPlugins

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
        ToastUtils.init(application)
        //异常监听
        CrashHandler.init()
        //初始化RxHttp
        RxHttpPlugins.init(AppComponent.globalConfig?.okHttpClient)
            .setDebug(BuildConfig.DEBUG)
    }

    override fun onTerminate(application: Application) {

    }


}