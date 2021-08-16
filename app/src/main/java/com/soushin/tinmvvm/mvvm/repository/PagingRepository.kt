package com.soushin.tinmvvm.mvvm.repository

import com.soushin.tinmvvm.mvvm.repository.entity.ArticleListEntity
import io.reactivex.rxjava3.core.Single
import me.soushin.tinmvvm.base.BaseRepository
import rxhttp.wrapper.param.RxHttp

class PagingRepository : BaseRepository() {

    fun requestData(page: Int): Single<ArticleListEntity> {
        return RxHttp.get("/article/list/${page}/json")
            .setSync()
            .asBaseResponse(ArticleListEntity::class.java)
            .single(ArticleListEntity())
        /*return Single.just(page).map {
            val list = mutableListOf<CategoryEntity>()
            for (i in 1..20) {
                val id = (page - 1) * 20 + i
                list.add(CategoryEntity(id = id))
            }
            return@map list
        }*/
    }

    /*fun requestData(page: Int): MutableList<CategoryEntity> {
        val list = mutableListOf<CategoryEntity>()
        for (i in 1..20) {
            val id = (page - 1) * 20 + i
            list.add(CategoryEntity(id = id))
        }
        return list
    }*/
}