package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import android.os.Build
import android.os.Environment
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.blankj.ALog
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.app.AppData
import com.soushin.tinmvvm.mvvm.repository.HomeRepository
import com.soushin.tinmvvm.mvvm.repository.entity.ViewTaskEvent
import com.soushin.tinmvvm.mvvm.ui.fragment.HomeFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.soushin.tinmvvm.base.BaseApp
import me.soushin.tinmvvm.base.BaseViewModel

class HomeViewModel(application: Application) :
    BaseViewModel<HomeRepository>(application, HomeRepository()) {
    var tvContent= MutableLiveData("Hello World")
    val viewEvent = MutableLiveData<ViewTaskEvent>()

    fun loadData(){
        getLifecycleScope().launch {
            withContext(Dispatchers.IO){//"WorkManager","Create Fragment","Coroutine","TabLayout、ViewPager2",
                val list = mutableListOf<String>("Multiplex Layout",
                    "BaseRecyclerViewAdapter","RxPermissions","Thread Pool",
                    "App Crash","Base Service","File Read Write")
                viewEvent.postValue(ViewTaskEvent(key = 0,value = list))
            }
        }
    }


    fun onItemClick(item:String,v:View,position: Int){
        ///防止连续点击 当快速点击的时候Navigation会可能会出
        //java.lang.IllegalArgumentException: Navigation action/destination com.soushin.tinmvvm:id/action_homeFragment_to_recyclerFragment cannot be found from the current destination Destination(com.soushin.tinmvvm:id/categoryFragment) label=CategoryFragment class=com.soushin.tinmvvm.mvvm.ui.fragment.CategoryFragment
        //这是因为当前的destination已经不是HomeFragment 而是已经换成了categoryFragment 所以这时候再去action_homeFragment_to_recyclerFragment必然会抛异常
        //所以跳转之前最好判断当前页面是否是HomeFragment
        //这在页面跳转时添加转场动画会显得非常明显(转场动画有duration)
        ALog.e("onItemClick",item,position)
        val navController = Navigation.findNavController(v)
        ALog.e("点击切换数据了",navController.currentDestination?.label)
        if (navController.currentDestination?.id != R.id.homeFragment) return
        when(position){
            0->{
                navController.navigate(HomeFragmentDirections.actionHomeFragmentToMultiplexFragment(),AppData.get().queryNavOptions())
            }
            1->{
                navController.navigate(HomeFragmentDirections.actionHomeFragmentToRecyclerFragment(),AppData.get().queryNavOptions())
            }
            2->{
                viewEvent.value = ViewTaskEvent(key = 1)
            }
            3->{
                navController.navigate(HomeFragmentDirections.actionHomeFragmentToThreadPoolFragment(),AppData.get().queryNavOptions())
            }
            4->{
                throw RuntimeException("模拟java运行时异常")
            }
            5->{
                viewEvent.value = ViewTaskEvent(key = 2)
            }
            6->{
                getLifecycleScope().launch {
                    withContext(Dispatchers.IO){
                        ALog.i("getExternalStorageDirectory", Environment.getExternalStorageDirectory().absolutePath)
                        ALog.i("getExternalStoragePublicDirectory",
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath)
                        ALog.i("getDataDirectory", Environment.getDataDirectory().absolutePath)
                        ALog.i("getDownloadCacheDirectory", Environment.getDownloadCacheDirectory().absolutePath)
                        ALog.i("getRootDirectory", Environment.getRootDirectory().absolutePath)
                        ALog.i("DIRECTORY_MUSIC",getApplication<BaseApp>().getExternalFilesDir(
                            Environment.DIRECTORY_MUSIC)?.absolutePath)
                        ALog.i("DIRECTORY_ALARMS",getApplication<BaseApp>().getExternalFilesDir(
                            Environment.DIRECTORY_ALARMS)?.absolutePath)
                        ALog.i("DIRECTORY_DCIM",getApplication<BaseApp>().getExternalFilesDir(
                            Environment.DIRECTORY_DCIM)?.absolutePath)
                        ALog.i("DIRECTORY_DOCUMENTS",getApplication<BaseApp>().getExternalFilesDir(
                            Environment.DIRECTORY_DOCUMENTS)?.absolutePath)
                        ALog.i("DIRECTORY_DOWNLOADS",getApplication<BaseApp>().getExternalFilesDir(
                            Environment.DIRECTORY_DOWNLOADS)?.absolutePath)
                        ALog.i("DIRECTORY_MOVIES",getApplication<BaseApp>().getExternalFilesDir(
                            Environment.DIRECTORY_MOVIES)?.absolutePath)
                        ALog.i("DIRECTORY_NOTIFICATIONS",getApplication<BaseApp>().getExternalFilesDir(
                            Environment.DIRECTORY_NOTIFICATIONS)?.absolutePath)
                        ALog.i("DIRECTORY_PICTURES",getApplication<BaseApp>().getExternalFilesDir(
                            Environment.DIRECTORY_PICTURES)?.absolutePath)
                        ALog.i("DIRECTORY_PODCASTS",getApplication<BaseApp>().getExternalFilesDir(
                            Environment.DIRECTORY_PODCASTS)?.absolutePath)
                        ALog.i("DIRECTORY_RINGTONES",getApplication<BaseApp>().getExternalFilesDir(
                            Environment.DIRECTORY_RINGTONES)?.absolutePath)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                            ALog.i("DIRECTORY_SCREENSHOTS",getApplication<BaseApp>().getExternalFilesDir(
                                Environment.DIRECTORY_SCREENSHOTS)?.absolutePath)
                            ALog.i("DIRECTORY_AUDIOBOOKS",getApplication<BaseApp>().getExternalFilesDir(
                                Environment.DIRECTORY_AUDIOBOOKS)?.absolutePath)
                        }
                    }
                }
            }
        }
    }


}