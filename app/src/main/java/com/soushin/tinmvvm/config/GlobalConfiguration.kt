package com.soushin.tinmvvm.config

import android.app.Application
import android.content.Context
import com.blankj.ALog
import com.soushin.tinmvvm.BuildConfig
import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener
import me.soushin.tinmvvm.config.GlobalConfigModule
import me.soushin.tinmvvm.listener.ALoger
import me.soushin.tinmvvm.listener.AppLifecycle
import me.soushin.tinmvvm.listener.ConfigModule
import me.soushin.tinmvvm.listener.ExceptionCallBack
import xcrash.ILogger
import xcrash.XCrash

/**
 * 全局配置
 * @auther SouShin
 * @time 2020/7/15 15:34
 */
class GlobalConfiguration :ConfigModule {

    override fun applyOptions(context: Context, builder: GlobalConfigModule.Builder) {
        builder
            .exceptionCallback(object :ExceptionCallBack{
                override fun exceptionCallback(type: Int, logPath: String, emergency: String) {
                    //这里的type java错误 1  anr 错误 2  native 错误 3
                    ALog.e("滴滴滴崩溃了",type,logPath,emergency);
                }
            })
            .errorResponseListener(ResponseErrorListenerImpl())
//            .logOutputCallback(object :ALoger{
//                override fun log(type: Int, tag: String?, vararg content: Any?) {
//                    //自定义crash异常打印的工具类 可以把crash日志存储到自定义文件里
//                }
//            })
//            .logDir("")//crash日志输出文件夹
            .logEnable(BuildConfig.DEBUG)
            .build()
    }

    override fun injectAppLifecycle(context: Context, lifecycles: MutableList<AppLifecycle>) {
        lifecycles.add(AppLifecycleImpl())
    }

    override fun injectActivityLifecycle(
        context: Context?,
        lifecycles: MutableList<Application.ActivityLifecycleCallbacks>
    ) {
        lifecycles.add(ActivityLifecycleCallbacksImpl())
    }
}