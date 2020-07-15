package me.soushin.tinmvvm.listener

import android.app.Application
import android.content.Context

/**
 * 用于代理 {@link Application} 的生命周期
 * @auther SouShin
 * @time 2020/7/15 13:18
 */
interface AppLifecycle {

    fun attachBaseContext(base: Context)

    fun onCreate(application: Application)

    fun onTerminate(application: Application)

}