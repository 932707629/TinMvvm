package com.soushin.tinmvvm.mvvm.repository.datasource

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.soushin.tinmvvm.mvvm.repository.PagingRepository
import com.soushin.tinmvvm.mvvm.repository.entity.CategoryEntity
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class SimpleDataSource(private val repository: PagingRepository) : RxPagingSource<Int,CategoryEntity>() {

    override fun getRefreshKey(state: PagingState<Int, CategoryEntity>): Int? {
        return null
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, CategoryEntity>> {
        //页码未定义置为1
        val currentPage = params.key ?: 1
        return repository.requestData(currentPage)
            .subscribeOn(Schedulers.io())
            .map {
                return@map LoadResult.Page(it,null,
                    currentPage.plus(1),LoadResult.Page.COUNT_UNDEFINED,
                    LoadResult.Page.COUNT_UNDEFINED) as LoadResult<Int, CategoryEntity>
            }.onErrorReturn { return@onErrorReturn LoadResult.Error(it) }
    }


    /*override fun getRefreshKey(state: PagingState<Int, CategoryEntity>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CategoryEntity> {
        return try {
            //页码未定义置为1
            val currentPage = params.key ?: 1
            val result = repository.requestData(currentPage)
            ALog.i("load",Thread.currentThread().name,currentPage);
            LoadResult.Page(result,null,currentPage.plus(1))

        }catch (e:Exception){
            LoadResult.Error(throwable = e)
        }
    }*/


}