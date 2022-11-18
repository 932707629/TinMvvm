package com.soushin.tinmvvm.app

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.blankj.ALog
import com.hjq.toast.ToastUtils
import com.tencent.mmkv.MMKV
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
        initMMkv(application)
        //初始化RxHttp
        RxHttpPlugins.init(AppComponent.globalConfig?.okHttpClient)
            .setDebug(GlobalConstants.isDebug())
    }

    private fun initMMkv(application: Application) {
        val rootDir = MMKV.initialize(application)
        ALog.i("mmkv缓存路径: $rootDir");
//        AppData.get().init()//初始化缓存数据
    }


    override fun onTerminate(application: Application) {

    }


}