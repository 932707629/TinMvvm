package com.soushin.tinmvvm.mvvm.model.entity

/**
 * 返回data是Any
 * @author created by Soushin
 * @time 2020/1/17 14 56
 */
data class BaseResponse<T>(
    var `data`: T?,
    var errorCode: Int,// 0
    var errorMsg: String?
){

    fun isOk():Boolean{
        return errorCode==0
    }

}