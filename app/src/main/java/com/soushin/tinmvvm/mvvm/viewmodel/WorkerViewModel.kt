package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.work.Data
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.blankj.ALog
import com.soushin.tinmvvm.base.BaseViewModel
import com.soushin.tinmvvm.mvvm.model.WorkerModel
import com.soushin.tinmvvm.worker.BackGroundWorker
import java.util.concurrent.TimeUnit

/**
 * @author created by Soushin
 * @time 2020/1/10 15 28
 */
class WorkerViewModel :BaseViewModel<WorkerModel> {

    constructor(application: Application):super(application,WorkerModel())

    var btnStatus= MutableLiveData<String>("开始任务")
    //PeriodicWorkRequest OneTimeWorkRequest
    val request=PeriodicWorkRequest.Builder(BackGroundWorker::class.java,15,TimeUnit.MINUTES)
        .setInputData(Data.Builder().putString("key","value").build())
//        .setConstraints(Constraints.Builder()
//            .setRequiredNetworkType(NetworkType.CONNECTED)//在网络连接的时候执行
//            .setRequiresBatteryNotLow(true)//不在电量不足时执行
//            .setRequiresCharging(true)//在充电时执行
//            .setRequiresDeviceIdle(true)//在待机状态执行
//            .setRequiresStorageNotLow(true)//不在存储容量不足时执行
//            .build())
        .build()

    fun changeStatus(owner: LifecycleOwner){

        if (btnStatus.value=="开始任务"){
            btnStatus.value="结束任务"
            val state= WorkManager.getInstance().enqueue(request)

            WorkManager.getInstance().getWorkInfoByIdLiveData(request.id).observe(owner,
                Observer {
                    ALog.e("接收任务返回结果",it.outputData.getString("result"))
                })

        }else{
            btnStatus.value="开始任务"
            WorkManager.getInstance().cancelWorkById(request.id)
        }
    }



}