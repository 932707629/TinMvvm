package com.soushin.tinmvvm

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.blankj.ALog
import com.soushin.tinmvvm.base.BaseActivity
import com.soushin.tinmvvm.base.useFragment
import com.soushin.tinmvvm.utils.AppManager

/**
 * 对每个activity实现监听
 * 并且可以添加一些需求
 * @author created by Soushin
 * @time 2020/1/7 13 22
 */
class ActivityLifecycleCallbacksImpl : Application.ActivityLifecycleCallbacks {

    var fragmentLifecycleMap= hashMapOf<String,FragmentLifecycleCallbacksImpl>()

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        ALog.i("onActivityCreated",activity.localClassName)
        AppManager.get().addActivity(activity)
        if (activity is FragmentActivity&&activity.useFragment()){//注册fragment回调监听
            val fragmentLifecycleCallbacksImpl=FragmentLifecycleCallbacksImpl()
            fragmentLifecycleMap.put(activity.localClassName,fragmentLifecycleCallbacksImpl)
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacksImpl,true)
        }
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
        if (activity is FragmentActivity&&activity.useFragment()){//注册fragment回调监听
            fragmentLifecycleMap.get(activity.localClassName)?.let {
                activity.supportFragmentManager.unregisterFragmentLifecycleCallbacks(it)
            }
        }
    }

}