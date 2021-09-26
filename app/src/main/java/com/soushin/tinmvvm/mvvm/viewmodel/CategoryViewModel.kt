package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.*
import androidx.navigation.Navigation
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.blankj.ALog
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.app.AppData
import com.soushin.tinmvvm.app.code
import com.soushin.tinmvvm.app.msg
import com.soushin.tinmvvm.mvvm.repository.CategoryRepository
import com.soushin.tinmvvm.mvvm.repository.PagingRepository
import com.soushin.tinmvvm.mvvm.repository.datasource.PagingDataSource
import com.soushin.tinmvvm.mvvm.repository.entity.ViewTaskEvent
import com.soushin.tinmvvm.mvvm.ui.fragment.CategoryFragmentDirections
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.soushin.tinmvvm.base.BaseViewModel
import me.soushin.tinmvvm.utils.throttleClick


/**
 * @author created by Soushin
 * @time 2020/1/14 18 05
 */
class CategoryViewModel(application: Application) :
    BaseViewModel<CategoryRepository>(application, CategoryRepository()) {

    var viewEvent= MutableLiveData<ViewTaskEvent>()

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        super.onStateChanged(source, event)
        if (event == Lifecycle.Event.ON_CREATE){
            viewModelScope.launch {
                mRepository.request()
                    .onSuccess { ALog.i("收到了返回的数据",it); }
                        //收到错误信息 回调到Error信息处理器
                    .onFailure { getErrorHandler()?.mHandlerFactory?.handleError(it) }
                    //或者自定义信息处理
//                    .onFailure { ALog.i("收到错误信息",it.code,it.msg); }
            }
        }
    }


    fun onViewClick() = throttleClick {
        when(it.id){
            R.id.fab_next->{
                ///自己跳转自己 多次重复打开一个页面
                //.setLaunchSingleTop(true)会让堆栈中始终保持fragment一个实例
                Navigation.findNavController(it).navigate(
                    CategoryFragmentDirections.actionCategoryFragmentSelf(20),
                    AppData.get().queryNavOptions()
                )
            }
            R.id.fab_last->{
                Navigation.findNavController(it).popBackStack();
            }
        }
    }

    fun getData() = Pager(PagingConfig(pageSize = 20,initialLoadSize = 20,prefetchDistance = 1)){
        PagingDataSource(PagingRepository())
    }.liveData


}