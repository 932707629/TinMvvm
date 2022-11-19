package com.soushin.tinmvvm.app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.blankj.ALog
import me.soushin.tinmvvm.base.DataBindingActivity

/**
 * 对每个activity实现监听
 * 并且可以添加一些需求
 * @author created by Soushin
 * @time 2020/1/7 13 22
 */
class ActivityLifecycleCallbacksImpl : Application.ActivityLifecycleCallbacks {
    private var isInitToolbar = "isInitToolbar"//是否初始化toolbar

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        ALog.i("onActivityCreated",activity.localClassName)
    }

    override fun onActivityResumed(activity: Activity) {
//        ALog.i("onActivityResumed",activity.localClassName)
    }

    override fun onActivityStarted(activity: Activity) {
        ALog.i("onActivityStarted",activity.localClassName)
        if (!activity.intent.getBooleanExtra(isInitToolbar, false)) {
            activity.intent.putExtra(isInitToolbar, true)

        }
    }

    override fun onActivityPaused(activity: Activity) {
//        ALog.i("onActivityPaused",activity.localClassName)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
//        ALog.i("onActivitySaveInstanceState",activity.localClassName)
    }

    override fun onActivityStopped(activity: Activity) {
//        ALog.i("onActivityStopped",activity.localClassName)
    }

    override fun onActivityDestroyed(activity: Activity) {
        ALog.i("onActivityDestroyed",activity.localClassName)
    }

}