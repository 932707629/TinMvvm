package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import me.soushin.tinmvvm.base.BaseViewModel
import com.soushin.tinmvvm.mvvm.repository.PagingRepository
import com.soushin.tinmvvm.mvvm.repository.datasource.PagingDataSource

class PagingViewModel(application: Application) :
    BaseViewModel<PagingRepository>(application, PagingRepository()) {

    //,initialLoadSize = 20,prefetchDistance = 1 ,initialKey = 1
    fun getData() = Pager(PagingConfig(pageSize = 20,initialLoadSize = 20,prefetchDistance = 1)){
        PagingDataSource(mRepository)
    }.liveData

    /*//,initialLoadSize = 20,prefetchDistance = 1 ,initialKey = 1
    fun getData() = Pager(PagingConfig(pageSize = 20)){
        PagingDataSource(PagingRepository())
    }.flow*/

}