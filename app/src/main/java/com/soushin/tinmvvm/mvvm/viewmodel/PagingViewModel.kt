package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.lifecycleScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import me.soushin.tinmvvm.base.BaseViewModel
import com.soushin.tinmvvm.mvvm.repository.PagingRepository
import com.soushin.tinmvvm.mvvm.repository.datasource.SimpleDataSource
import kotlinx.coroutines.launch

class PagingViewModel(application: Application) :
    BaseViewModel<PagingRepository>(application, PagingRepository()) {

    fun getData() = Pager(PagingConfig(pageSize = 1,initialLoadSize = 20)){
        SimpleDataSource(mRepository)
    }.flow



}