package me.soushin.tinmvvm.config

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import me.soushin.tinmvvm.base.BaseApp
import me.soushin.tinmvvm.base.DataBindingActivity
import me.soushin.tinmvvm.utils.AppManager

/**
 * Activity生命周期监听器
 * @author SouShin
 * @time 2021/11/12 17:19
 */
class TinActivityLifecycleImpl:Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        AppManager.get().addActivity(activity)

        if (activity is DataBindingActivity<*, *> &&activity.useFragment()){//注册fragment回调监听
            BaseApp.instance?.appLifecycle?.mFragmentLifecycle?.forEach {
                activity.supportFragmentManager.registerFragmentLifecycleCallbacks(it,true)
            }
        }
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
        AppManager.get().currentActivity=activity
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
        if (activity==AppManager.get().currentActivity){
            AppManager.get().currentActivity=null
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
        AppManager.get().removeActivity(activity)
        if (activity is DataBindingActivity<*, *> &&activity.useFragment()){//注册fragment回调监听
            BaseApp.instance?.appLifecycle?.mFragmentLifecycle?.forEach {
                activity.supportFragmentManager.unregisterFragmentLifecycleCallbacks(it)
            }
        }
    }
}