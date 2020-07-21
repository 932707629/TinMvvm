package me.soushin.tinmvvm.listener


/**
 * log日志输出接口 供外部实现自己的log组件
 * @auther SouShin
 * @time 2020/7/21 14:48
 */
interface ALoger {

    fun log(type:Int,tag:String?,vararg content: Any?)

}