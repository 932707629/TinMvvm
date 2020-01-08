package com.soushin.tinmvvm

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.blankj.ALog
import com.soushin.tinmvvm.utils.AppManager

/**
 * 对每个activity实现监听
 * 并且可以添加一些需求
 * @author created by Soushin
 * @time 2020/1/7 13 22
 */
class ActivityLifecycleCallbacksImpl : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        ALog.i("onActivityCreated",activity.localClassName)
        AppManager.get().addActivity(activity)
    }

    override fun onActivityResumed(activity: Activity) {
        ALog.i("onActivityResumed",activity.localClassName)
    }

    override fun onActivityStarted(activity: Activity) {
        ALog.i("onActivityStarted",activity.localClassName)
    }

    override fun onActivityPaused(activity: Activity) {
        ALog.i("onActivityPaused",activity.localClassName)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        ALog.i("onActivitySaveInstanceState",activity.localClassName)
    }

    override fun onActivityStopped(activity: Activity) {
        ALog.i("onActivityStopped",activity.localClassName)
    }

    override fun onActivityDestroyed(activity: Activity) {
        ALog.i("onActivityDestroyed",activity.localClassName)
        AppManager.get().removeActivity(activity)
    }

}