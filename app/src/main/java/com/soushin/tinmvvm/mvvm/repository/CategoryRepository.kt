package com.soushin.tinmvvm.mvvm.repository

import com.soushin.tinmvvm.mvvm.repository.entity.CategoryEntity
import io.reactivex.rxjava3.core.Observable
import me.soushin.tinmvvm.base.BaseRepository
import rxhttp.wrapper.param.RxHttp

/**
 * @author created by Soushin
 * @time 2020/1/14 18 04
 */
class CategoryRepository : BaseRepository() {

    fun requestData(): Observable<MutableList<CategoryEntity>> {
        return RxHttp.get("/wxarticle/chapters/json")
            .asBaseResponseList(CategoryEntity::class.java)
    }

}