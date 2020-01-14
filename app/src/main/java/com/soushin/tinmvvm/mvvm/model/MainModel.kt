package com.soushin.tinmvvm.mvvm.model

import com.soushin.tinmvvm.base.BaseModel
import com.soushin.tinmvvm.mvvm.model.entity.CategoryEntity
import rxhttp.wrapper.param.RxHttp

/**
 *
 * @author created by Soushin
 * @time 2020/1/8 09 11
 */
class MainModel :BaseModel(){


    fun getDatas():String{

        return "1231231312"
    }

    fun requestDatas():Any{

        return RxHttp.get("/wxarticle/chapters/json")
            .asObject(CategoryEntity::class.java)
            .subscribe()
    }

}