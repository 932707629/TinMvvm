package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.blankj.ALog
import com.jeremyliao.liveeventbus.LiveEventBus
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.app.GlobalConstants
import com.soushin.tinmvvm.app.throttleClick
import com.soushin.tinmvvm.mvvm.repository.CategoryRepository
import com.soushin.tinmvvm.mvvm.repository.PagingRepository
import com.soushin.tinmvvm.mvvm.repository.datasource.PagingDataSource
import com.soushin.tinmvvm.mvvm.repository.entity.ViewTaskEvent
import com.soushin.tinmvvm.mvvm.ui.fragment.CategoryFragment
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import me.soushin.tinmvvm.base.BaseViewModel


/**
 * @author created by Soushin
 * @time 2020/1/14 18 05
 */
class CategoryViewModel(application: Application) :
    BaseViewModel<CategoryRepository>(application, CategoryRepository()) {


    override fun onCreate(source: LifecycleOwner) {
        super.onCreate(source)

        source.lifecycleScope.launch {
            /*mRepository.request()
                .onSuccess { ALog.i("收到了返回的数据",it); }
                    //收到错误信息 回调到Error信息处理器
                .onFailure(getErrorHandler())
                //或者自定义信息处理
//                    .onFailure { ALog.i("收到错误信息",it.code,it.msg); }*/
            /*mRepository.requestFlow()
                .catch(getErrorHandler())
                .collect {
                    ALog.i("收到了返回的数据",it);
                }*/
            flow {
                var count = 0
                while (true){
                    emit((count++).toLong())
                    delay(2000)
                }
            }.collect {
                ALog.i("收到数据打印信息了",it);
            }
        }
    }


    fun onViewClick() = throttleClick {
        when(it.id){
            R.id.fab_next->{
                ///自己跳转自己 多次重复打开一个页面
                LiveEventBus.get<ViewTaskEvent>(GlobalConstants.tag_main_view_event).post(
                    ViewTaskEvent(GlobalConstants.action_add, CategoryFragment.newInstance())
                )
            }
            R.id.fab_last->{
                LiveEventBus.get<ViewTaskEvent>(GlobalConstants.tag_main_view_event).post(ViewTaskEvent(GlobalConstants.action_back))
            }
        }
    }

    fun getData() = Pager(PagingConfig(pageSize = 20,initialLoadSize = 20,prefetchDistance = 1)){
        PagingDataSource(PagingRepository())
    }.liveData


}