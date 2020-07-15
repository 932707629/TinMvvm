package me.soushin.tinmvvm.config

/**
 * 框架参数配置 基于建造者模式
 * @auther SouShin
 * @time 2020/7/15 15:56
 */
class GlobalConfigModule {
    private val baseUrl:String

    constructor(builder:Builder){
        this.baseUrl=builder.baseUrl
    }

    class Builder(){

        var baseUrl:String=""

        fun baseUrl(baseUrl:String):Builder{
            this.baseUrl=baseUrl
            return this
        }

        fun build(): GlobalConfigModule{
            return GlobalConfigModule(this)
        }
    }


}