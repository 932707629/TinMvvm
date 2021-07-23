package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.soushin.tinmvvm.mvvm.repository.PagingRepository
import com.soushin.tinmvvm.mvvm.repository.SplashRepository
import com.soushin.tinmvvm.mvvm.repository.datasource.PagingDataSource
import com.soushin.tinmvvm.mvvm.repository.entity.CategoryEntity
import io.reactivex.rxjava3.core.Single
import me.soushin.tinmvvm.base.BaseViewModel

class SplashViewModel(application: Application) :
    BaseViewModel<SplashRepository>(application, SplashRepository()) {

    fun getData() = Pager(PagingConfig(pageSize = 10,initialLoadSize = 20,prefetchDistance = 1),initialKey = 1){
        PagingDataSource(PagingRepository())
    }.liveData
}