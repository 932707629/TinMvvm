package me.soushin.tinmvvm.listener

/**
 *
 * @auther SouShin
 * @time 2020/7/21 17:18
 */
interface ExceptionCallBack {

    //java错误 1  anr 错误 2  native 错误 3
    fun exceptionCallback(type:Int,logPath:String, emergency:String)

}