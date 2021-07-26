package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.blankj.ALog
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.mvvm.repository.CoroutineRepository
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
    BaseViewModel<CoroutineRepository>(application, CoroutineRepository()) {

    operator fun invoke(){
        ALog.i("调用invoke函数");
        //除此之外 使用 Lifecycle lifecycle-viewmodel-ktx 工件提供的 viewModelScope
        viewModelScope.launch {

        }
    }

    fun onClick(): View.OnClickListener {
        return View.OnClickListener {
            when(it.id){
                R.id.btn_coroutine_fuc->{
                    //协程的普通用法
                    getLifecycleScope()?.launch {
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
                    getLifecycleScope()?.launch {
                        launch(Dispatchers.IO) {
                            kotlinx.coroutines.delay(5000)
                            ALog.e("协程delay执行结束", Thread.currentThread().name);

                            withContext(Dispatchers.Main) {
                                ALog.e("协程这里可以改变UI", Thread.currentThread().name);
                            }
                        }
                    }

                    ALog.e("所有协程任务都已开始")
                }
                R.id.btn_coroutine_crash->{
                    //协程内的异常捕获
                    getLifecycleScope()?.launch(coroutineExceptionHandler){
                        throw RuntimeException("RuntimeException in nested coroutine")
                    }
                }
                R.id.btn_advanced_grammar->{
                    getLifecycleScope()?.launch {
                        withContext(Dispatchers.IO){
                            val list = mutableListOf("1","2","3","2","3","2","3","2","3","4","5","6","7","8","9","0")

                            ALog.i("filter输出文本内容",list.filter {str-> return@filter TextUtils.equals(str,"3") })

                            ALog.i("filterIndexed输出文本内容",list.filterIndexed { index, s -> index==3  })

                            ALog.i("reduceRight输出文本内容",list.reduceRight { acc, s ->
                                ALog.i("reduceRight",acc,s);
                                return@reduceRight acc
                            })
                            ALog.i("输出原始文本内容",list)
                            ///数据去重
                            ALog.i("distinct输出文本内容",list.distinct())
                            //跳过前5位数
                            ALog.i("drop输出文本内容",list.drop(5))
                            //顺序打乱
                            list.shuffle()
                            ALog.i("shuffle",list);
                            //排序
                            list.sort()
                            ALog.i("sort",list);
                            //数据填充
                            list.fill("6")
                            ALog.i("fill",list);
                            //Call requires API level 24
                            /*list.removeIf {
                                return@removeIf true
                            }*/
                        }
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
