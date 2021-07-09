package com.soushin.tinmvvm.worker

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.blankj.ALog

/**
 * 代替service做后台任务的
 * @author created by Soushin
 * @time 2020/1/10 13 14
 */
class OneTimeWorker(context: Context, parameters: WorkerParameters) :
    Worker(context, parameters) {

    override fun doWork(): Result {
        ALog.e("传入数据",inputData.getString("key"),Thread.currentThread().name)
        return Result.success(Data.Builder().putString("result","success").build())
    }

    override fun onStopped() {
        super.onStopped()
        ALog.e("BackGroundWorker","onStopped")
    }

}