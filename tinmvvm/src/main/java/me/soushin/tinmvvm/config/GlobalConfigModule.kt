package me.soushin.tinmvvm.config

import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener
import me.soushin.tinmvvm.listener.ALoger
import me.soushin.tinmvvm.listener.ExceptionCallBack


/**
 * 框架参数配置 基于建造者模式
 * @auther SouShin
 * @time 2020/7/15 15:56
 */
class GlobalConfigModule {
    var logDir:String?=null//log日志输出路径 这个值如果不为空默认开启日志缓存到文件功能 默认关闭
    var logEnable:Boolean=true//是否开启log日志输出到控制台 默认开启
//    var logOutputCallback:ALoger?=null//log日志输出回调
    var exceptionCallBack:ExceptionCallBack?=null//监听系统异常回调
    var errorResponseListener: ResponseErrorListener?=null//rxjava2异常监听回调

    constructor(builder: Builder) {
        this.logDir=builder.logDir
        this.logEnable=builder.logEnable
//        this.logOutputCallback=builder.logOutputCallback
        this.exceptionCallBack=builder.exceptionCallBack
        this.errorResponseListener=builder.errorResponseListener
    }

    class Builder() {

        var logDir:String?=null//log日志输出路径 这个值如果不为空默认开启日志缓存到文件功能 默认关闭
        var logEnable:Boolean=true//是否开启log日志输出到控制台 默认开启
//        var logOutputCallback:ALoger?=null//log日志输出回调
        var exceptionCallBack:ExceptionCallBack?=null//监听系统异常回调
        var errorResponseListener= ResponseErrorListener.EMPTY//rxjava2异常监听回调

        fun logDir(logDir:String):Builder{
            this.logDir=logDir
            return this
        }

        fun logEnable(logEnable:Boolean):Builder{
            this.logEnable=logEnable
            return this
        }

        /*fun logOutputCallback(logOutputCallback:ALoger):Builder{
            this.logOutputCallback=logOutputCallback
            return this
        }*/

        fun exceptionCallback(exceptionCallBack:ExceptionCallBack):Builder{
            this.exceptionCallBack=exceptionCallBack
            return this
        }

        fun errorResponseListener(errorResponseListener: ResponseErrorListener):Builder{
            this.errorResponseListener=errorResponseListener
            return this
        }

        /*fun okHttpConfig(builder:OkHttpClient.Builder):Builder{
            this.okhttpBuilder=builder
            return this
        }

        fun globalParamsFunction(globalParamsFunction:Function<Param<*>,Param<*>>?):Builder{
            this.globalParamsFunction=globalParamsFunction
            return this
        }

        fun resultDecoderFunction(resultDecoderFunction:Function<String,String>?):Builder{
            this.resultDecoderFunction=resultDecoderFunction
            return this
        }*/

        fun build(): GlobalConfigModule {
            return GlobalConfigModule(this)
        }
    }


}