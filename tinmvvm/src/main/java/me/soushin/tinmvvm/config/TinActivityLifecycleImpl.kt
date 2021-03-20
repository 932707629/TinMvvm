package me.soushin.tinmvvm.config

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import me.soushin.tinmvvm.utils.AppManager

class TinActivityLifecycleImpl:Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        AppManager.get().addActivity(activity)
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
    }
}