package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blankj.ALog
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.mvvm.model.CoroutineModel
import kotlinx.coroutines.*
import me.soushin.tinmvvm.base.BaseViewModel

/**
 * ================================================
 * Description:
 * <p>
 * Created by TinMvvmTemplate on 03/06/2020 14:42
 * ================================================
 */

class CoroutineViewModel(application: Application) :
    BaseViewModel<CoroutineModel>(application, CoroutineModel()) {

    fun onClick(): View.OnClickListener {
        return View.OnClickListener {
            when(it.id){
                R.id.btn_coroutine_fuc->{

                    launch {
                        val task1 = withContext(Dispatchers.IO) {
                            Thread.sleep(2000)
                            ALog.e("task1执行完毕", Thread.currentThread().name);
                        }

                        val task2 = async(Dispatchers.IO) {
                            Thread.sleep(2000)
                            ALog.e("task2执行完毕", Thread.currentThread().name);
                            return@async "task2"
                        }
                        val task3 = async(Dispatchers.IO) {
                            Thread.sleep(2000)
                            ALog.e("task3执行完毕", Thread.currentThread().name);
                            return@async "task3"
                        }

                        ALog.e("所有任务都已执行", Thread.currentThread().name, task2.await(), task3.await());
                    }

                    launch {
                        launch(Dispatchers.IO) {
                            kotlinx.coroutines.delay(5000)
                            ALog.e("协程delay执行结束", Thread.currentThread().name);

                            withContext(Dispatchers.Main) {
                                ALog.e("协程这里可以改变UI", Thread.currentThread().name);
                            }
                        }
                    }
                    ALog.e("所有协程任务都已开始");
                }
                R.id.btn_coroutine_crash->{
                    //协程内的异常捕获
                    launch(coroutineExceptionHandler){
                        throw RuntimeException("RuntimeException in nested coroutine")
                    }
                }
            }
                //用GlobalScope需要协程的生命周期和app生命周期一样长
                /*GlobalScope.launch {

                    val task1= withContext(Dispatchers.IO){
                        Thread.sleep(2000)
                        ALog.e("task1执行完毕",Thread.currentThread().name);
                    }

                    val task2=async(Dispatchers.IO) {
                        Thread.sleep(2000)
                        ALog.e("task2执行完毕",Thread.currentThread().name);
                        return@async "task2"
                    }
                    val task3=async(Dispatchers.IO) {
                        Thread.sleep(2000)
                        ALog.e("task3执行完毕",Thread.currentThread().name);
                        return@async "task3"
                    }

                    ALog.e("所有任务都已执行",Thread.currentThread().name,task2.await(),task3.await());
                }


                GlobalScope.launch {
                    launch(Dispatchers.IO) {
                        kotlinx.coroutines.delay(5000)
                        ALog.e("协程delay执行结束",Thread.currentThread().name);

                        withContext(Dispatchers.Main){
                            ALog.e("协程这里可以改变UI",Thread.currentThread().name);
                        }
                    }
                }
                ALog.e("所有协程任务都已开始");*/
        }
    }

    /*override val coroutineExceptionHandler: CoroutineExceptionHandler
        get() = CoroutineExceptionHandler { coroutineContext, exception ->
            ALog.i("Handle $exception in CoroutineExceptionHandler")
        }*/


}
