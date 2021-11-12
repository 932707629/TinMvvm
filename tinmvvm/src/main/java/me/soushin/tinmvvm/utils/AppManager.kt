package me.soushin.tinmvvm.utils

import android.app.Activity
import android.content.Intent
import android.os.Process
import com.blankj.ALog
import java.util.*
import kotlin.system.exitProcess

/**
 * Activity管理工具类
 * @author created by Soushin
 * @time 2020/1/8 16 06
 */
class AppManager private constructor(){

    companion object {
        private val instance: AppManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            AppManager()
        }

        @JvmStatic
        fun get() : AppManager {
            return instance
        }
    }

    private val activitys by lazy { mutableListOf<Activity>() }

    fun getActivityCount():Int{
        return activitys.size
    }

    fun addActivity(activity: Activity){
        activitys.add(activity)
    }

    fun removeActivity(clazz: Class<*>){
        activitys.forEach {
            if (it.javaClass==clazz){
                removeActivity(it)
            }
        }
    }

    fun removeActivity(target: Activity){
        activitys.remove(target)
    }


    fun getTopActivity():Activity?{
        if (activitys.isEmpty()){
            ALog.w("stack == null when getTopActivity()");
            return null
        }
        return activitys.last()
    }

    /**
     * 获取在前台的 {@link Activity} (保证获取到的 {@link Activity} 正处于可见状态, 即未调用 {@link Activity#onStop()}), 获取的 {@link Activity} 存续时间
     * 是在 {@link Activity#onStop()} 之前, 所以如果当此 {@link Activity} 调用 {@link Activity#onStop()} 方法之后, 没有其他的 {@link Activity} 回到前台(用户返回桌面或者打开了其他 App 会出现此状况)
     * 这时调用 {@link #getCurrentActivity()} 有可能返回 {@code null}, 所以请注意使用场景和 {@link #getTopActivity()} 不一样
     * <p>
     * Example usage:
     * 使用场景比较适合, 只需要在可见状态的 {@link Activity} 上执行的操作
     * 如当后台 {@link Service} 执行某个任务时, 需要让前台 {@link Activity} ,做出某种响应操作或其他操作,如弹出 {@link Dialog}, 这时在 {@link Service} 中就可以使用 {@link #getCurrentActivity()}
     * 如果返回为 {@code null}, 说明没有前台 {@link Activity} (用户返回桌面或者打开了其他 App 会出现此状况), 则不做任何操作, 不为 {@code null}, 则弹出 {@link Dialog}
     *
     * @return
     */
    var currentActivity:Activity?=null


    fun go(clazz: Class<*>,finish:Boolean=false,newTask:Boolean=false){
        val intent=Intent(getTopActivity(),clazz)
        if (newTask){
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        getTopActivity()?.startActivity(intent)
        if (finish){
            getTopActivity()?.finish()
        }
    }

    fun isAlive(clazz: Class<*>):Boolean{
        activitys.forEach {
            if (it.javaClass==clazz){
                return true
            }
        }
        return false
    }

    fun findActivity(clazz: Class<*>):Activity?{
        activitys.forEach {
            if (it.javaClass==clazz){
                return it
            }
        }
        return null
    }

    /**
     * 获取所有未被销毁的activity集合
     */
    fun getAllActivity():MutableList<Activity>{
        return activitys
    }

    fun finish(clazz:Class<*>){
        synchronized(AppManager::class.java){
            val it= activitys.iterator()
            it.let {
                while (it.hasNext()){
                    val next=it.next()
                    if (clazz.simpleName==next.javaClass.simpleName){
                        next.finish()
                        it.remove()
                    }
                }
            }
        }
    }

    fun finish(activity: Activity){
        finish(activity.javaClass)
    }

    fun killAll(){
        synchronized(AppManager::class.java){
            activitys.forEach { it.finish() }
            activitys.clear()
        }
    }

    /**
     * 释放系统资源
     */
    fun release(){
        activitys.clear()
        currentActivity=null
    }

    /**
     * 退出应用程序
     *
     * 此方法经测试在某些机型上并不能完全杀死 App 进程, 几乎试过市面上大部分杀死进程的方式, 但都发现没卵用, 所以此
     * 方法如果不能百分之百保证能杀死进程, 就不能贸然调用 [.release] 释放资源, 否则会造成其他问题, 如果您
     * 有测试通过的并能适用于绝大多数机型的杀死进程的方式, 望告知
     */
    fun appExit() {
        try {
            killAll()
            Process.killProcess(Process.myPid())
            exitProcess(0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}