package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import android.os.Build
import android.view.View
import com.blankj.ALog
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.mvvm.repository.ThreadPoolRepository
import me.soushin.tinmvvm.base.BaseViewModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

/**
 * ================================================
 * Description:现在大多都是用rxjava 忽略了线程池
 * 其实rxjava底层也是基于线程池和handler的 新知识我们需要学 旧的也不能丢
 * <p>
 * Created by TinMvvmTemplate on 06/24/2020 11:28
 * ================================================
 */

class ThreadPoolViewModel(application: Application) :
    BaseViewModel<ThreadPoolRepository>(application, ThreadPoolRepository()) {

    private var newCachedThreadPool:ExecutorService?=null
    private var newFixedThreadPool:ExecutorService?=null
    private var newScheduledThreadPool: ScheduledExecutorService?=null
    private var newSingleThreadPool:ExecutorService?=null
    private var newWorkStealingPool:ExecutorService?=null

    fun onClicker()=View.OnClickListener {
        when(it.id){
            R.id.btn_cache_thread->{
                ///可以有无限大的线程数进来（线程地址不一样）
                ALog.i("****************************newCachedThreadPool*******************************");
                newCachedThreadPool= Executors.newCachedThreadPool()
                for (i in 1..3){
                    newCachedThreadPool?.execute(ThreadForPools(i.toLong()))
                }
            }
            R.id.btn_fixed_thread->{
                ///每次只有两个线程在处理，当第一个线程执行完毕后，新的线程进来开始处理（线程地址不一样）
                ALog.i("****************************newFixedThreadPool*******************************");
                newFixedThreadPool = Executors.newFixedThreadPool(2);
                for (i in 1..3){
                    newFixedThreadPool?.execute(ThreadForPools(i.toLong()))
                }
            }
            R.id.btn_scheduled_thread->{
                ///延迟三秒之后执行，除了延迟执行之外和newFixedThreadPool基本相同，可以用来执行定时任务
                ALog.i("****************************newScheduledThreadPool*******************************");
                newScheduledThreadPool = Executors.newScheduledThreadPool(2);
                for (i in 1..3){
                    newScheduledThreadPool?.schedule(ThreadForPools(i.toLong()),3, TimeUnit.SECONDS)
                }
            }
            R.id.btn_single_thread->{
                ///只存在一个线程，顺序执行
                ALog.i("****************************newSingleThreadPool*******************************");
                newSingleThreadPool = Executors.newSingleThreadExecutor();
                for (i in 1..3){
                    newSingleThreadPool?.execute(ThreadForPools(i.toLong()))
                }
            }
            R.id.btn_work_stealing->{
                ///一个并行的线程池，参数中传入的是一个线程并发的数量 不会保证任务的顺序执行，也就是 WorkStealing 的意思，抢占式的工作
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                    ALog.i("****************************newWorkStealingPool*******************************");
                    newWorkStealingPool = Executors.newWorkStealingPool();
                    for (i in 1..3){
                        newWorkStealingPool?.execute(ThreadForPools(i.toLong()))
                    }
                } else {
                    ALog.i("当前环境不满足newWorkStealingPool执行条件，限制android SDK大于等于24 并且用java1.8的时候");
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        shutdown()
    }

    //中断线程池
    private fun shutdown(){
        //强制关闭线程池
        try {
            newCachedThreadPool?.shutdownNow()
            newFixedThreadPool?.shutdownNow()
            newScheduledThreadPool?.shutdownNow()
            newSingleThreadPool?.shutdownNow()
            newWorkStealingPool?.shutdownNow()
        }catch (e:InterruptedException){
            e.printStackTrace()
        }
    }

    /**
     * 正常关闭流程应该是这样的
     */
    fun shutdown(threadPool:ExecutorService?){
        try {
            threadPool?.let {
                it.shutdown() //通知各个子线程该结束了
                //如果有子线程在限定时间内还没有结束  就强制结束
                if (!it.awaitTermination(1, TimeUnit.SECONDS)){
                    //超时的时候向线程池中所有的线程发出中断（interrupted）
                    it.shutdownNow()
                }
            }
        }catch(e:InterruptedException){
            e.printStackTrace()
        }
    }

    class ThreadForPools(var index: Long) : Runnable{

        override fun run() {
            try {
                ALog.i("开始执行线程任务");
                Thread.sleep(index*1000);
                ALog.i("我的线程标识是${Thread.currentThread().name}");
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }


}
