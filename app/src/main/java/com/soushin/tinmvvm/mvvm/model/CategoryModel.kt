package com.soushin.tinmvvm.mvvm.model

import com.soushin.tinmvvm.mvvm.model.entity.CategoryEntity
import io.reactivex.Observable
import me.soushin.tinmvvm.base.BaseModel
import rxhttp.wrapper.param.RxHttp

/**
 * @author created by Soushin
 * @time 2020/1/14 18 04
 */
class CategoryModel : BaseModel() {

    fun requestDatas():Observable<List<CategoryEntity>>{
        return RxHttp.get("/wxarticle/chapters/json")
            .asBaseResponseList(CategoryEntity::class.java)

    }

}