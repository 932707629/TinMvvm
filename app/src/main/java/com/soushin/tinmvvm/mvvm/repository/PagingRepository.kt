package com.soushin.tinmvvm.mvvm.repository

import com.soushin.tinmvvm.mvvm.repository.entity.CategoryEntity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import me.soushin.tinmvvm.base.BaseRepository

class PagingRepository : BaseRepository() {

    fun requestData(page:Int):Single<List<CategoryEntity>>{
        return Single.just(page).map {
            val list = mutableListOf<CategoryEntity>()
            for (i in 1..20){
                list.add(CategoryEntity())
            }
            return@map list
        }
    }

    /*fun requestData(page:Int):MutableList<CategoryEntity>{
        val list = mutableListOf<CategoryEntity>()
        for (i in 0..20){
            list.add(CategoryEntity())
        }
        return list
    }*/
}