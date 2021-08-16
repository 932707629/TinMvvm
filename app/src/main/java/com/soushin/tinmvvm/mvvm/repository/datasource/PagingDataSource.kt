package com.soushin.tinmvvm.mvvm.repository.datasource

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.blankj.ALog
import com.soushin.tinmvvm.app.utils.RxUtils
import com.soushin.tinmvvm.app.utils.toJson
import com.soushin.tinmvvm.mvvm.repository.PagingRepository
import com.soushin.tinmvvm.mvvm.repository.entity.Article
import io.reactivex.rxjava3.core.Single

class PagingDataSource(private val repository: PagingRepository) : RxPagingSource<Int,Article>() {

    /*override fun getRefreshKey(state: PagingState<Int, CategoryEntity>): Int? {
        ALog.i("getRefreshKey",state.anchorPosition,state.config.toJson(),state.pages.toJson());
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CategoryEntity> {
        //页码未定义置为1
        val currentPage = params.key ?: 1
        val nextPage = if (currentPage == 20) null else currentPage + 1

        ALog.i("current page",params.toJson(),currentPage,nextPage);
        val result = repository.requestData(currentPage)
        return LoadResult.Page(
            data = result,
            prevKey = null,///向前翻页设置此参数
            nextKey = nextPage)
    }*/

    /**当执行adapter.refresh()方法时会回调到这个方法里**/
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        ALog.i("getRefreshKey",state.anchorPosition,state.config.toJson(),state.pages.toJson());
        return null/*state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }*/
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Article>> {
        //页码未定义置为1
        val currentPage = params.key ?: 0
        val nextPage = /*if (currentPage == 20) null else*/ currentPage + 1

        ALog.i("current page",params.toJson(),currentPage,nextPage);
        return repository.requestData(currentPage)
            .map {
                ALog.i("current thread ${Thread.currentThread().name}",it.size);
                return@map LoadResult.Page(
                    data = it.datas?: mutableListOf(),
                    prevKey = null,///向前翻页设置此参数
                    nextKey = nextPage) as LoadResult<Int, Article>
            }.onErrorReturn {
                ALog.i("current thread ${Thread.currentThread().name}");
                return@onErrorReturn LoadResult.Error(it)
            }.compose(RxUtils.applySingleAsync())
    }



}