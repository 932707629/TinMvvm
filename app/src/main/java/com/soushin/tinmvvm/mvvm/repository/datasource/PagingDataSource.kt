package com.soushin.tinmvvm.mvvm.repository.datasource

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.blankj.ALog
import com.soushin.tinmvvm.app.utils.RxUtils
import com.soushin.tinmvvm.app.utils.toJson
import com.soushin.tinmvvm.mvvm.repository.PagingRepository
import com.soushin.tinmvvm.mvvm.repository.entity.CategoryEntity
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class PagingDataSource(private val repository: PagingRepository) : RxPagingSource<Int,CategoryEntity>() {

    override fun getRefreshKey(state: PagingState<Int, CategoryEntity>): Int? {
        ALog.i("getRefreshKey",state.anchorPosition,state.config.toJson(),state.pages.toJson());
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, CategoryEntity>> {
        //页码未定义置为1
        val currentPage = params.key ?: 1
        val nextPage = if (currentPage == 20) null else currentPage + 1

        ALog.i("current page",params.toJson(),currentPage,nextPage);
        return repository.requestData(currentPage)
            .map {
                ALog.i("current thread ${Thread.currentThread().name}");
                return@map LoadResult.Page(
                    data = it,
                    prevKey = null,///向前翻页设置此参数
                    nextKey = nextPage) as LoadResult<Int, CategoryEntity>
            }.onErrorReturn {
                ALog.i("current thread ${Thread.currentThread().name}");
                return@onErrorReturn LoadResult.Error(it)
            }.compose(RxUtils.applySingleAsync())
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