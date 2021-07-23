package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import me.soushin.tinmvvm.base.BaseViewModel
import com.soushin.tinmvvm.mvvm.repository.PagingRepository
import com.soushin.tinmvvm.mvvm.repository.datasource.PagingDataSource

class PagingViewModel(application: Application) :
    BaseViewModel<PagingRepository>(application, PagingRepository()) {

    fun getData() = Pager(PagingConfig(pageSize = 10,initialLoadSize = 20,prefetchDistance = 1),initialKey = 1){
        PagingDataSource(mRepository)
    }.liveData

    /*fun getData() = Pager(PagingConfig(pageSize = 1,initialLoadSize = 20)){
        PagingDataSource(mRepository)
    }.flow*/

}