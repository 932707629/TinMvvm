package com.soushin.tinmvvm.mvvm.model

import com.rxjava.rxlife.ObservableLife
import com.rxjava.rxlife.RxLife
import com.soushin.tinmvvm.mvvm.model.entity.CategoryEntity
import me.soushin.base_lib.base.BaseModel
import rxhttp.wrapper.param.RxHttp

/**
 * @author created by Soushin
 * @time 2020/1/14 18 04
 */
class CategoryModel : BaseModel() {

    fun requestDatas():ObservableLife<List<CategoryEntity>>{

        return RxHttp.get("/wxarticle/chapters/json")
            .asBaseListResponse(CategoryEntity::class.java)
            .`as`(RxLife.asOnMain(weakReference?.get()))
    }

}