package me.soushin.tinmvvm.config

import android.annotation.SuppressLint
import android.app.Application
import cat.ereza.customactivityoncrash.CustomActivityOnCrash
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.blankj.ALog
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.soushin.tinmvvm.base.BaseApp
import me.soushin.tinmvvm.mvvm.ui.TinErrorActivity

/**
 * 拥有此类即可调用对应的方法拿到对应实例
 * @auther SouShin
 * @time 2020/7/15 16:37
 */
class AppComponent// TODO: 2020/7/15  这里拿到全局配置信息即可实现对全局参数统一配置
    (globalConfig: GlobalConfigModule) {

    init {
        AppComponent.globalConfig = globalConfig
        initALog(BaseApp.instance!!)
        initXcrash()
        initRxErrorHandler()
    }

    @SuppressLint("RestrictedApi")
    private fun initXcrash() {
        //整个配置属性，可以设置一个或多个，也可以一个都不设置
        CaocConfig.Builder.create()
            //程序在后台时，发生崩溃的三种处理方式
            //BackgroundMode.BACKGROUND_MODE_SHOW_CUSTOM: //当应用程序处于后台时崩溃，也会启动错误页面，
            //BackgroundMode.BACKGROUND_MODE_CRASH:      //当应用程序处于后台崩溃时显示默认系统错误（一个系统提示的错误对话框），
            //BackgroundMode.BACKGROUND_MODE_SILENT:     //当应用程序处于后台时崩溃，默默地关闭程序！
            .backgroundMode(CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM)
            .enabled(true)     //false表示对崩溃的拦截阻止。用它来禁用customactivityoncrash框架
            .showErrorDetails(false) //这将隐藏错误活动中的“错误详细信息”按钮，从而隐藏堆栈跟踪,—》针对框架自带程序崩溃后显示的页面有用(DefaultErrorActivity)。。
            .showRestartButton(false)    //是否可以重启页面,针对框架自带程序崩溃后显示的页面有用(DefaultErrorActivity)。
            .trackActivities(true)     //错误页面中显示错误详细信息；针对框架自带程序崩溃后显示的页面有用(DefaultErrorActivity)。
            .minTimeBetweenCrashesMs(2000)      //定义应用程序崩溃之间的最短时间，以确定我们不在崩溃循环中。比如：在规定的时间内再次崩溃，框架将不处理，让系统处理！
//            .restartActivity(MainActivity.class)      //重新启动后的页面
                .errorActivity(TinErrorActivity::class.java) //程序崩溃后显示的页面
//                    .eventListener()//设置监听
                    .apply()
        //如果没有任何配置，程序崩溃显示的是默认的设置
        CustomActivityOnCrash.install(BaseApp.instance)

    }


    fun initALog(app: Application) {
        ALog.init(app)
            .setLogSwitch(globalConfig?.logEnable!!) // 设置log总开关，包括输出到控制台和文件，默认开
            .setConsoleSwitch(globalConfig?.logEnable!!) // 设置是否输出到控制台开关，默认开
            .setGlobalTag(null) // 设置log全局标签，默认为空
            // 当全局标签不为空时，我们输出的log全部为该tag，
            // 为空时，如果传入的tag为空那就显示类名，否则显示tag
            .setLogHeadSwitch(true) // 设置log头信息开关，默认为开
            .setLog2FileSwitch(globalConfig?.logDir!=null) // 打印log时是否存到文件的开关，默认关
            .setDir(globalConfig?.logDir) // 当自定义路径为空时，写入应用的/cache/log/目录中
            .setFilePrefix("") // 当文件前缀为空时，默认为"alog"，即写入文件为"alog-MM-dd.txt"
            .setBorderSwitch(false) // 输出日志是否带边框开关，默认开
            .setConsoleFilter(ALog.V) // log的控制台过滤器，和logcat过滤器同理，默认Verbose
            .setFileFilter(ALog.V).stackDeep = 1 // log栈深度，默认为1
    }

    companion object {
        var rxErrorHandler:RxErrorHandler?=null
        var globalConfig: GlobalConfigModule?=null
    }

    private fun initRxErrorHandler() {
        rxErrorHandler=RxErrorHandler.builder()
            .with(BaseApp.instance)
            .responseErrorListener(globalConfig?.errorResponseListener)
            .build()
    }

}