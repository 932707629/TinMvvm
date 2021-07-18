package com.soushin.tinmvvm.app.service

import com.blankj.ALog
import com.soushin.tinmvvm.app.utils.RxUtils
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.soushin.tinmvvm.base.BaseService
import me.soushin.tinmvvm.utils.AppManager
import java.util.concurrent.TimeUnit

class MyService : BaseService() {

    override fun init() {
        ALog.i("自定义service创建",this.javaClass.simpleName)
        val disposable= Observable.interval(10,TimeUnit.SECONDS)
            .compose(RxUtils.applyAsync())
            .subscribe({
                ALog.i("轮训任务执行",it);
                ALog.i("检查Activity任务队列",AppManager.get().getActivityCount());
            }, {
                it.printStackTrace()
            })

        addDispose(disposable)

        //协程
        val jop=launch {
            ALog.i("协程任务开始",Thread.currentThread().name);
            withContext(Dispatchers.IO){
                ALog.i("协程任务进行中",Thread.currentThread().name);
                withContext(Dispatchers.Main){
                    ALog.i("协程任务结束",Thread.currentThread().name);
                }
            }
        }

        jop.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        ALog.i("service停止运行了");
    }

}
