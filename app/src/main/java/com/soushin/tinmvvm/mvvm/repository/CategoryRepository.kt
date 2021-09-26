package com.soushin.tinmvvm.mvvm.repository

import com.soushin.tinmvvm.mvvm.repository.entity.CategoryEntity
import io.reactivex.rxjava3.core.Observable
import me.soushin.tinmvvm.base.BaseRepository
import okhttp3.internal.wait
import rxhttp.awaitResult
import rxhttp.toList
import rxhttp.toStr
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toBaseResponse
import java.lang.IllegalArgumentException

/**
 * @author created by Soushin
 * @time 2020/1/14 18 04
 */
class CategoryRepository : BaseRepository() {

    fun requestData(): Observable<MutableList<CategoryEntity>> {
        return RxHttp.get("/wxarticle/chapters/json")
            .asBaseResponseList(CategoryEntity::class.java)
    }

    /**
     * RxHttp 协程的使用介绍
     * https://juejin.cn/post/6844904100090347528#heading-2
     */
    suspend fun request():Result<MutableList<CategoryEntity>>{
        return RxHttp.get("/wxarticle/chapters/json")
            .toBaseResponse<MutableList<CategoryEntity>>()
            .awaitResult()
    }

}