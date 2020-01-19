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
class BackGroundWorker :Worker {

    constructor(context: Context,parameters:WorkerParameters):super(context,parameters)

    override fun doWork(): Result {
        ALog.e("传入数据打印",inputData.getString("key"))

        return Result.success(Data.Builder().putString("result","success").build())
    }

    override fun onStopped() {
        super.onStopped()
        ALog.e("BackGroundWorker","onStopped")
    }

}