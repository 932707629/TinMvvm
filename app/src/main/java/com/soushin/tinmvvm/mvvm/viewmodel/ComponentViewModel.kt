package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.blankj.ALog
import com.soushin.tinmvvm.mvvm.repository.ComponentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.soushin.tinmvvm.base.BaseViewModel

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
//        val navController = Navigation.findNavController(v)
//        ALog.e("点击切换数据了",navController.currentDestination?.label)
//        if (navController.currentDestination?.id != R.id.componentFragment) return
        when(position){
            0->{
                // TODO:  ComponentFragmentDirections
//                navController.navigate(
//                    ComponentFragmentDirections.actionComponentFragmentToWorkerFragment(),
//                    AppData.get().queryNavOptions()
//                )
            }
            1->{
                // TODO:  ComponentFragmentDirections
//                navController.navigate(
//                    ComponentFragmentDirections.actionComponentFragmentToCategoryFragment(99),
//                    AppData.get().queryNavOptions()
//                )
            }
            2->{
                // TODO:  ComponentFragmentDirections
//                navController.navigate(
//                    ComponentFragmentDirections.actionComponentFragmentToCoroutineFragment(),
//                    AppData.get().queryNavOptions()
//                )
            }
            3->{
                // TODO:  ComponentFragmentDirections
//                navController.navigate(
//                    ComponentFragmentDirections.actionComponentFragmentToTabLayoutViewpagerFragment(),
//                    AppData.get().queryNavOptions()
//                )
            }
            4->{
                // TODO:  ComponentFragmentDirections
//                navController.navigate(
//                    ComponentFragmentDirections.actionComponentFragmentToPagingFragment(),
//                    AppData.get().queryNavOptions()
//                )
            }
            5->{
                // TODO:  ComponentFragmentDirections
//                navController.navigate(
//                    ComponentFragmentDirections.actionComponentFragmentToDataBindingLayoutFragment(),
//                    AppData.get().queryNavOptions()
//                )
            }
        }
    }


}