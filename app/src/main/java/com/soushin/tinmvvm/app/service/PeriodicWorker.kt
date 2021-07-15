package com.soushin.tinmvvm.app.service

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.blankj.ALog

/**
 * 定时任务
 */
class PeriodicWorker(context: Context, parameters: WorkerParameters) :
    Worker(context, parameters) {

    override fun doWork(): Result {
        //传入数据以及执行程序所在线程
        ALog.e("传入数据",inputData.getString("key"),Thread.currentThread().name)
        return Result.success()
    }

}