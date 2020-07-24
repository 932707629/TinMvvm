package com.soushin.tinmvvm.config

import android.app.Activity
import android.app.Application
import android.content.Context
import com.blankj.ALog
import com.soushin.tinmvvm.BuildConfig
import me.soushin.tinmvvm.config.GlobalConfigModule
import me.soushin.tinmvvm.listener.AppLifecycle
import me.soushin.tinmvvm.listener.ConfigModule
import me.soushin.tinmvvm.listener.ExceptionCallBack

/**
 * 全局配置
 * @auther SouShin
 * @time 2020/7/15 15:34
 */
class GlobalConfiguration :ConfigModule {

    override fun applyOptions(context: Context, builder: GlobalConfigModule.Builder) {
        builder
            .exceptionCallback(object :ExceptionCallBack{
                override fun exceptionCallback(errorActivity: Activity, emergency: String) {
                    //crash异常信息输出 可以把crash日志存储到自定义文件里
                    //这里的回调是在框架里的TinErrorActivity回调的  errorActivity后续生死交给开发者处理
                    //可以在这里显示错误弹窗或者其他业务逻辑
                    errorActivity.finish()
                    ALog.e("滴滴滴崩溃了",emergency);
                }
            })
            .errorResponseListener(ResponseErrorListenerImpl())
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