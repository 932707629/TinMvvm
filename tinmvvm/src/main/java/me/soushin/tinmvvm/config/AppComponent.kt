package me.soushin.tinmvvm.config

import android.app.Application
import com.blankj.ALog
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.soushin.tinmvvm.base.BaseApp

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
        initRxErrorHandler()
    }

    private fun initALog(app: Application) {
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