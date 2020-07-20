package me.soushin.tinmvvm.config

//import okhttp3.OkHttpClient
//import rxhttp.wrapper.callback.Function
//import rxhttp.wrapper.param.Param

/**
 * 框架参数配置 基于建造者模式
 * @auther SouShin
 * @time 2020/7/15 15:56
 */
class GlobalConfigModule {
//    var okhttpBuilder:OkHttpClient.Builder?
//    var globalParamsFunction:Function<Param<*>,Param<*>>?=null
//    var resultDecoderFunction:Function<String,String>?=null

    constructor(builder:Builder){
//        this.okhttpBuilder=builder.okhttpBuilder
    }

    class Builder(){

//        var okhttpBuilder:OkHttpClient.Builder?=null
//        var globalParamsFunction:Function<Param<*>,Param<*>>?=null
//        var resultDecoderFunction:Function<String,String>?=null

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

        fun build(): GlobalConfigModule{
            return GlobalConfigModule(this)
        }
    }


}