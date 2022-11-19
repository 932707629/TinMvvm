package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.blankj.ALog
import com.jeremyliao.liveeventbus.LiveEventBus
import com.soushin.tinmvvm.app.GlobalConstants
import com.soushin.tinmvvm.mvvm.repository.ComponentRepository
import com.soushin.tinmvvm.mvvm.repository.entity.ViewTaskEvent
import com.soushin.tinmvvm.mvvm.ui.fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.soushin.tinmvvm.base.BaseViewModel
import me.soushin.tinmvvm.base.DataBindingFragment

class ComponentViewModel(application: Application) :
    BaseViewModel<ComponentRepository>(application, ComponentRepository()) {

    var viewEvent = MutableLiveData<MutableList<String>>()

    fun loadData(){
        getScope().launch {
            withContext(Dispatchers.IO){
                val list = mutableListOf("WorkManager","Navigation", "Coroutine",
                    "TabLayout、ViewPager2","Paging","DataBinding")
                viewEvent.postValue(list)
            }
        }
    }

    fun onItemClick(item:String, v: View, position: Int){
        ALog.e("onItemClick",item,position)
        when(position){
            0->{
                LiveEventBus.get<ViewTaskEvent>(GlobalConstants.tag_main_view_event).post(
                    ViewTaskEvent(GlobalConstants.action_add, WorkerFragment.newInstance())
                )
            }
            1->{
                LiveEventBus.get<ViewTaskEvent>(GlobalConstants.tag_main_view_event).post(
                    ViewTaskEvent(GlobalConstants.action_add, CategoryFragment.newInstance())
                )
            }
            2->{
                LiveEventBus.get<ViewTaskEvent>(GlobalConstants.tag_main_view_event).post(
                    ViewTaskEvent(GlobalConstants.action_add, CoroutineFragment.newInstance())
                )
            }
            3->{
                LiveEventBus.get<ViewTaskEvent>(GlobalConstants.tag_main_view_event).post(
                    ViewTaskEvent(GlobalConstants.action_add, TabLayoutViewpager2Fragment.newInstance())
                )
            }
            4->{
                LiveEventBus.get<ViewTaskEvent>(GlobalConstants.tag_main_view_event).post(
                    ViewTaskEvent(GlobalConstants.action_add, PagingFragment.newInstance())
                )
            }
            5->{
                LiveEventBus.get<ViewTaskEvent>(GlobalConstants.tag_main_view_event).post(
                    ViewTaskEvent(GlobalConstants.action_add, DataBindingLayoutFragment.newInstance())
                )
            }
        }
    }


}