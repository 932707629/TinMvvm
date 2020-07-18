package me.soushin.tinmvvm.config

import okhttp3.OkHttpClient

/**
 * 框架参数配置 基于建造者模式
 * @auther SouShin
 * @time 2020/7/15 15:56
 */
class GlobalConfigModule {
    var okhttpBuilder:OkHttpClient.Builder?
    var globalParamsMap:MutableMap<String,Any>?=null
    var globalHeaderMap:MutableMap<String,Any>?=null

    constructor(builder:Builder){
        this.okhttpBuilder=builder.okhttpBuilder
        this.globalHeaderMap=builder.globalHeaderMap
        this.globalParamsMap=builder.globalParamsMap
    }

    class Builder(){

        var okhttpBuilder:OkHttpClient.Builder?=null
        var globalParamsMap:MutableMap<String,Any>?=null
        var globalHeaderMap:MutableMap<String,Any>?=null

        fun okHttpConfig(builder:OkHttpClient.Builder):Builder{
            this.okhttpBuilder=builder
            return this
        }

        fun globalParamsMap(paramsMap:MutableMap<String,Any>?):Builder{
            this.globalParamsMap=paramsMap
            return this
        }

        fun globalHeaderMap(headerMap:MutableMap<String,Any>?):Builder{
            this.globalHeaderMap=headerMap
            return this
        }

        fun build(): GlobalConfigModule{
            return GlobalConfigModule(this)
        }
    }


}