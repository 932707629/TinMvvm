package com.soushin.tinmvvm.base

import android.app.Application
import com.blankj.ALog
import com.hjq.toast.ToastUtils
import com.soushin.tinmvvm.ActivityLifecycleCallbacksImpl
import com.soushin.tinmvvm.BuildConfig
import com.soushin.tinmvvm.widget.ToastStyle

/**
 *
 * @author created by Soushin
 * @time 2020/1/7 13 12
 */
class App :Application(){
    override fun onCreate() {
        super.onCreate()

        initALog(this)
        ToastUtils.init(this,ToastStyle())
        registerActivityLifecycleCallbacks(ActivityLifecycleCallbacksImpl())
    }

    fun initALog(app: Application) {
        ALog.init(app)
            .setLogSwitch(BuildConfig.DEBUG) // 设置log总开关，包括输出到控制台和文件，默认开
            .setConsoleSwitch(BuildConfig.DEBUG) // 设置是否输出到控制台开关，默认开
            .setGlobalTag(null) // 设置log全局标签，默认为空
            // 当全局标签不为空时，我们输出的log全部为该tag，
            // 为空时，如果传入的tag为空那就显示类名，否则显示tag
            .setLogHeadSwitch(true) // 设置log头信息开关，默认为开
            .setLog2FileSwitch(false) // 打印log时是否存到文件的开关，默认关
            .setDir("") // 当自定义路径为空时，写入应用的/cache/log/目录中
            .setFilePrefix("") // 当文件前缀为空时，默认为"alog"，即写入文件为"alog-MM-dd.txt"
            .setBorderSwitch(false) // 输出日志是否带边框开关，默认开
            .setConsoleFilter(ALog.V) // log的控制台过滤器，和logcat过滤器同理，默认Verbose
            .setFileFilter(ALog.V).stackDeep = 1 // log栈深度，默认为1
    }




}