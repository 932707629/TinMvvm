package me.soushin.tinmvvm.config

import me.soushin.tinmvvm.base.BaseViewModel

/**
 * 基础配置类
 */
class DataBindingConfig{
    var layoutId:Int?=null
    var variableId:Int?=null
    var vmClass:Class<out BaseViewModel<*>>?=null
    val bindingParams:MutableMap<Int,Any> = mutableMapOf()
    var dataBindingComponent: Any?=null

    constructor(
        layoutId: Int?,
        variableId: Int?,
        vmClass: Class<out BaseViewModel<*>>?,
        dataBindingComponent: Any?
    ) {
        this.layoutId = layoutId
        this.variableId = variableId
        this.vmClass = vmClass
        this.dataBindingComponent = dataBindingComponent
    }

    constructor(layoutId: Int?, variableId: Int?, vmClass: Class<out BaseViewModel<*>>?) {
        this.layoutId = layoutId
        this.variableId = variableId
        this.vmClass = vmClass
    }

    constructor()

    fun addBindingParam(variableId: Int, any: Any): DataBindingConfig{
        bindingParams[variableId] = any
        return this
    }

}


