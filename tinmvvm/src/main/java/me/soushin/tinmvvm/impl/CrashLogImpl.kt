package me.soushin.tinmvvm.impl

import android.util.Log
import com.blankj.ALog
import me.soushin.tinmvvm.listener.ALoger
import xcrash.ILogger

/**
 *
 * @auther SouShin
 * @time 2020/7/21 17:32
 */
class CrashLogImpl : ILogger {
    private var aloger:ALoger?=null

    constructor(aloger:ALoger?){
        this.aloger=aloger
    }

    //自定义crash的日志输出
    override fun i(tag: String?, msg: String?) {
        log(Log.INFO,tag,msg)
    }

    override fun i(tag: String?, msg: String?, tr: Throwable?) {
        log(Log.INFO,tag,msg,tr)
    }

    override fun w(tag: String?, msg: String?) {
        log(Log.WARN,tag,msg)
    }

    override fun w(tag: String?, msg: String?, tr: Throwable?) {
        log(Log.WARN,tag,msg,tr)
    }

    override fun v(tag: String?, msg: String?) {
        log(Log.VERBOSE,tag,msg)
    }

    override fun v(tag: String?, msg: String?, tr: Throwable?) {
        log(Log.VERBOSE,tag,msg,tr)
    }

    override fun e(tag: String?, msg: String?) {
        log(Log.ERROR,tag,msg)
    }

    override fun e(tag: String?, msg: String?, tr: Throwable?) {
        log(Log.ERROR,tag,msg,tr)
    }

    override fun d(tag: String?, msg: String?) {
        log(Log.DEBUG,tag,msg)
    }

    override fun d(tag: String?, msg: String?, tr: Throwable?) {
        log(Log.DEBUG,tag,msg,tr)
    }

    private fun log(type: Int, tag: String?, vararg msg: Any?) {
        if (aloger==null){
            ALog.log(type,tag,msg)
        }else{
            aloger?.log(type,tag,msg)
        }
    }
}