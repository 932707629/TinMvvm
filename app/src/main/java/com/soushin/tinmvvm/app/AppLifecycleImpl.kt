package com.soushin.tinmvvm.app

import android.app.Application
import android.content.Context
import com.blankj.ALog
import com.hjq.toast.ToastUtils
import com.soushin.tinmvvm.BuildConfig
import com.tencent.mmkv.MMKV
import leakcanary.AppWatcher
import me.soushin.tinmvvm.config.AppComponent
import me.soushin.tinmvvm.listener.AppLifecycle
import rxhttp.RxHttpPlugins
import java.util.concurrent.TimeUnit

/**
 * 功能相当于application
 * @auther SouShin
 * @time 2020/7/15 15:40
 */
class AppLifecycleImpl :AppLifecycle {

    override fun attachBaseContext(base: Context) {
//        MultiDex.install(base)
    }

    override fun onCreate(application: Application) {
        ToastUtils.init(application)
        initMMkv(application)
        //初始化RxHttp
        RxHttpPlugins.init(AppComponent.globalConfig?.okHttpClient)
            .setDebug(BuildConfig.DEBUG)
        initLeakCanary(BuildConfig.DEBUG)
    }

    private fun initMMkv(application: Application) {
        val rootDir = MMKV.initialize(application)
        ALog.i("mmkv缓存路径: $rootDir");
//        AppData.get().init()//初始化缓存数据
    }

    /***
     * 初始化内存泄露检测工具
     * @param enable 是否可用
     */
    private fun initLeakCanary(enable:Boolean) {
        AppWatcher.config = AppWatcher.config.copy(
            watchActivities = enable,
            watchFragments = enable,
            watchFragmentViews = enable,
            watchViewModels = enable,
            watchDurationMillis = TimeUnit.SECONDS.toMillis(5),
            enabled = enable,
        )
    }

    override fun onTerminate(application: Application) {

    }


}