package me.soushin.tinmvvm.config

import com.blankj.ALog
import me.soushin.tinmvvm.listener.ResponseErrorListener
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


/**
 * 框架参数配置 基于建造者模式
 * @auther SouShin
 * @time 2020/7/15 15:56
 */
class GlobalConfigModule(builder: Builder) {
    var logDir: String? = null//log日志输出路径 这个值如果不为空默认开启日志缓存到文件功能 默认关闭
    var debug: Boolean = true//是否开启log日志输出到控制台 默认开启
    //    var logOutputCallback:ALoger?=null//log日志输出回调
    var okHttpClient:OkHttpClient ?= null//提供个默认的可修改的okhttpclient全局共用
    var errorResponseListener: ResponseErrorListener? = null//rxjava2异常监听回调

    init {
        this.logDir = builder.logDir
        this.debug = builder.debug
        this.okHttpClient = builder.okHttpClient
        this.errorResponseListener = builder.errorResponseListener
    }

    class Builder {

        var logDir: String? = null//log日志输出路径 这个值如果不为空默认开启日志缓存到文件功能 默认关闭
        var debug: Boolean = true//是否开启log日志输出到控制台 默认开启
        //        var logOutputCallback:ALoger?=null//log日志输出回调
        var defaultOkHttpConfig= OkHttpConfigBean()//默认okhttp配置类,如果setOkHttpClient了只配置将不会生效
        var okHttpClient = getDefaultOkHttpClient()//提供默认的可修改的okhttpclient全局共用
        var errorResponseListener: ResponseErrorListener = ResponseErrorListener.EMPTY//rxjava2异常监听回调

        fun logDir(logDir: String): Builder {
            this.logDir = logDir
            return this
        }

        fun setDebug(debug: Boolean): Builder {
            this.debug = debug
            return this
        }

        /*fun logOutputCallback(logOutputCallback:ALoger):Builder{
            this.logOutputCallback=logOutputCallback
            return this
        }*/

        fun errorResponseListener(errorResponseListener: ResponseErrorListener): Builder {
            this.errorResponseListener = errorResponseListener
            return this
        }

        fun setDefaultOkHttpClient(okHttpConfigBean: OkHttpConfigBean): Builder{
            this.defaultOkHttpConfig=okHttpConfigBean
            return this
        }

        fun setOkHttpClient(okHttpClient: OkHttpClient): Builder {
            this.okHttpClient = okHttpClient
            return this
        }

        /*fun globalParamsFunction(globalParamsFunction:Function<Param<*>,Param<*>>?):Builder{
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

        private fun getDefaultOkHttpClient(): OkHttpClient {
            ALog.i("打印下okhttp配置${defaultOkHttpConfig}");
            return OkHttpClient.Builder()
                .callTimeout(defaultOkHttpConfig.callTimeout, TimeUnit.SECONDS)
                .connectTimeout(defaultOkHttpConfig.connectTimeout, TimeUnit.SECONDS)
                .writeTimeout(defaultOkHttpConfig.writeTimeout, TimeUnit.SECONDS)
                .readTimeout(defaultOkHttpConfig.readTimeout, TimeUnit.SECONDS)
                .sslSocketFactory(defaultOkHttpConfig.sslSocketFactory!!, defaultOkHttpConfig.trustManager!!) //添加信任证书
                .hostnameVerifier(defaultOkHttpConfig.hostnameVerifier!!).build()
        }
    }


}