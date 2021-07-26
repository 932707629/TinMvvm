package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.work.Data
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.blankj.ALog
import com.soushin.tinmvvm.app.service.OneTimeWorker
import com.soushin.tinmvvm.app.service.PeriodicWorker
import com.soushin.tinmvvm.mvvm.repository.WorkerRepository
import me.soushin.tinmvvm.base.BaseViewModel
import java.util.concurrent.TimeUnit

/**
 * @author created by Soushin
 * @time 2020/1/10 15 28
 */
class WorkerViewModel(application: Application) :
    BaseViewModel<WorkerRepository>(application, WorkerRepository()) {

    val workManager = WorkManager.getInstance(getApplication())
    var btnStatus = MutableLiveData("开始任务")

    //PeriodicWorkRequest OneTimeWorkRequest
    val request by lazy {
        ///定时任务最短15分钟
        PeriodicWorkRequest.Builder(PeriodicWorker::class.java, 15, TimeUnit.MINUTES)
//        OneTimeWorkRequestBuilder<OneTimeWorker>()
            .setInputData(Data.Builder().putString("key", "value").build()).build()
            /*.setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)//在网络连接的时候执行
                    .setRequiresBatteryNotLow(true)//不在电量不足时执行
                    .setRequiresCharging(true)//在充电时执行
//                  .setRequiresDeviceIdle(true)//在待机状态执行
                    .setRequiresStorageNotLow(true)//不在存储容量不足时执行
                    .build()
            ).build()*/
    }

    fun changeStatus() {
        if (btnStatus.value == "开始任务") {
            btnStatus.value = "结束任务"
            workManager.enqueue(request)
            workManager.getWorkInfoByIdLiveData(request.id).observe(lifecycle!!,{
                ALog.i("收到返回结果的",it.id,it.outputData.toString(),it.progress.toString(),it.runAttemptCount,it.state,it.tags);
            })
        } else {
            btnStatus.value = "开始任务"
            ///取消所有任务
            workManager.getWorkInfosByTag(OneTimeWorker::class.java.name)
                .get().forEach {
                    ALog.i("取消work",it.id,it.outputData.toString(),it.progress.toString(),it.runAttemptCount,it.state,it.tags);
                    workManager.cancelWorkById(it.id)
                }
        }
    }


}