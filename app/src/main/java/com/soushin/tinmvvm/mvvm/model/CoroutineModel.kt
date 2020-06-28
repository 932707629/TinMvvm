package com.soushin.tinmvvm.mvvm.model

import com.blankj.ALog
import kotlinx.coroutines.*
import me.soushin.base_lib.base.BaseModel

/**
 * ================================================
 * Description:
 * <p>
 * Created by TinMvvmTemplate on 03/06/2020 14:42
 * ================================================
 */

class CoroutineModel : BaseModel() {

    fun start() {
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