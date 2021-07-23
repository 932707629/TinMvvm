package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.blankj.ALog
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.app.AppData
import com.soushin.tinmvvm.mvvm.repository.ComponentRepository
import com.soushin.tinmvvm.mvvm.ui.fragment.ComponentFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.soushin.tinmvvm.base.BaseViewModel

class ComponentViewModel(application: Application) :
    BaseViewModel<ComponentRepository>(application, ComponentRepository()) {

    var viewEvent= MutableLiveData<MutableList<String>>()

    fun loadData(){
        getLifecycleScope().launch {
            withContext(Dispatchers.IO){
                val list = mutableListOf<String>("WorkManager","Navigation", "Coroutine","TabLayout、ViewPager2","Paging")
                viewEvent.postValue(list)
            }
        }
    }

    fun onItemClick(item:String, v: View, position: Int){
        ALog.e("onItemClick",item,position)
        val navController = Navigation.findNavController(v)
        ALog.e("点击切换数据了",navController.currentDestination?.label)
        if (navController.currentDestination?.id != R.id.componentFragment) return
        when(position){
            0->{
                navController.navigate(
                    ComponentFragmentDirections.actionComponentFragmentToWorkerFragment(),
                    AppData.get().queryNavOptions())
            }
            1->{
//              FragmentUtils.add(childFragmentManager, CategoryFragment(),R.id.fl_container)
                navController.navigate(
                    ComponentFragmentDirections.actionComponentFragmentToCategoryFragment(),
                    AppData.get().queryNavOptions())
            }
            2->{
                navController.navigate(
                    ComponentFragmentDirections.actionComponentFragmentToCoroutineFragment(),
                    AppData.get().queryNavOptions())
            }
            3->{
                navController.navigate(
                    ComponentFragmentDirections.actionComponentFragmentToTabLayoutViewpager2Fragment(),
                    AppData.get().queryNavOptions())
            }
            4->{
                navController.navigate(
                    ComponentFragmentDirections.actionComponentFragmentToPagingFragment(),
                    AppData.get().queryNavOptions()
                )
            }
        }
    }


}