package me.soushin.tinmvvm.config

import me.soushin.tinmvvm.base.BaseViewModel

/**
 * 基础配置类
 */
data class DataBindingConfig(
    var layoutId:Int?=null,
    var variableId:Int?=null,
    var vmClass:Class<out BaseViewModel<*>>?=null,
    val bindingParams:MutableMap<Int,Any> = mutableMapOf(),
    var dataBindingComponent: Any?=null,
){

    fun addBindingParam(variableId: Int, any: Any): DataBindingConfig{
        bindingParams[variableId] = any
        return this
    }

}


