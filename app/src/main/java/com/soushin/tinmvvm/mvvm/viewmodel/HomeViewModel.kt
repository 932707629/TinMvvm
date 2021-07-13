package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import android.os.Build
import android.os.Environment
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import com.blankj.ALog
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.config.throttleClick
import com.soushin.tinmvvm.mvvm.model.HomeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.soushin.tinmvvm.base.BaseApp
import me.soushin.tinmvvm.base.BaseViewModel

class HomeViewModel(application: Application) :
    BaseViewModel<HomeModel>(application, HomeModel()) {
    var tvContent= MutableLiveData("Hello World")
    val viewEvent = MutableLiveData<Int>()

    fun getData(){
        launch {
            withContext(Dispatchers.IO){
                tvContent.postValue(model.getData())
            }
        }
    }

    fun onClick():View.OnClickListener{
        ///防止连续点击
        return throttleClick {
            ALog.e("点击切换数据了")
            when(it.id){
                R.id.btn_change->{
                    getData()
                }
                R.id.btn_return->{
                    tvContent.value="Hello World"
                }
                R.id.btn_worker->{
                    Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_workerFragment)
                }
                R.id.btn_create_fragment->{
//                    FragmentUtils.add(childFragmentManager, CategoryFragment(),R.id.fl_container)
                    Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_categoryFragment)
                }
                R.id.btn_multiplex->{
                    Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_multiplexFragment)
                }
                R.id.btn_recycler->{
                    Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_recyclerFragment)
                }
                R.id.btn_coroutine->{
                    Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_coroutineFragment)
                }
                R.id.btn_permission->{
                    viewEvent.value = 1
                }
                R.id.btn_thread_pool->{
                    Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_threadPoolFragment)
                }
                R.id.btn_crash->{
                    throw RuntimeException("模拟java运行时异常")
                }
                R.id.btn_service->{
                    viewEvent.value = 2
                }
                R.id.btn_file_write->{
                    launch {
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


}