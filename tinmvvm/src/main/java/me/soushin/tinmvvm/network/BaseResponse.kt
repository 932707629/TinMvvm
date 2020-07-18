package me.soushin.tinmvvm.network

/**
 * 返回data是Any
 * @author created by Soushin
 * @time 2020/1/17 14 56
 */
data class BaseResponse<T>(
    private var `data`: T?,
    private var errorCode: Int,// 0
    private var errorMsg: String?
){

    fun isOk():Boolean{
        return errorCode==0
    }

    fun getData():T?{
        return data
    }

    fun setData(data:T?){
        this.data=data
    }

    fun getCode():Int{
        return errorCode
    }

    fun getMessage():String?{
        return errorMsg
    }




}