package me.soushin.tinmvvm.utils

import android.app.Activity
import android.content.Intent
import android.os.Process
import java.util.*
import kotlin.system.exitProcess

/**
 * Activity管理工具类
 * @author created by Soushin
 * @time 2020/1/8 16 06
 */
class AppManager private constructor(){

    companion object {

        internal var stack=Stack<Activity>()

        fun get() : AppManager {
            val instance: AppManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
                AppManager()
            }
            return  instance
        }
    }

    fun getActivityCount():Int{
        return stack.size
    }

    fun addActivity(activity: Activity){
        stack.add(activity)
    }

    fun removeActivity(target: Activity){
        stack.remove(target)
    }

    fun getCurrent():Activity?{
        return stack.lastElement()
    }

    fun go(clazz: Class<*>,finish:Boolean=false,newTask:Boolean=false){
        val intent=Intent(getCurrent(),clazz)
        if (newTask){
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        getCurrent()?.startActivity(intent)
        if (finish){
            getCurrent()?.finish()
        }
    }

    fun isAlive(clazz: Class<*>):Boolean{
        stack.forEach {
            if (it.javaClass==clazz){
                return true
            }
        }
        return false
    }

    fun findActivity(clazz: Class<*>):Activity?{
        stack.forEach {
            if (it.javaClass==clazz){
                return it
            }
        }
        return null
    }

    fun killAll(){
        synchronized(AppManager){
            stack.forEach {
                it.finish()
            }
            stack.clear()
        }
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