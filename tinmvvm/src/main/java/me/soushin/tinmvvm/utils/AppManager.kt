package me.soushin.tinmvvm.utils

import android.app.Activity
import java.util.*

/**
 * Activity管理工具类
 * @author created by Soushin
 * @time 2020/1/8 16 06
 */
class AppManager private constructor(){

    companion object {

        internal var stack: Stack<Activity>? = null

        fun get() : AppManager {
            val instance: AppManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
                AppManager()
            }
            return  instance
        }
    }

    fun getActivityCount():Int{
        return stack!!.size
    }

    fun addActivity(activity: Activity){
        if(stack ==null){
            stack = Stack()
        }
        stack?.apply {
            add(activity)
        }
    }

    fun removeActivity(target: Activity){
        stack?.apply {
            stack?.remove(target)
        }
    }


    fun finishTarget(cls : Class<*>){
        stack?.apply {
            for(activity in this){
                if(activity.javaClass == cls){
                    activity.finish()
                    stack?.remove(activity)
                    break
                }
            }
        }
    }

    fun finishAllWithoutTarget(cls : Class<*>){
        stack?.apply {
            for(activity in this){
                if(activity.javaClass != cls){
                    activity.finish()
                    stack?.remove(activity)
                }
            }
        }
    }


}