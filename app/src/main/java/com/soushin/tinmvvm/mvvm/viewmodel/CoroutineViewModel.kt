package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.blankj.ALog
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.app.utils.DataUtils
import com.soushin.tinmvvm.mvvm.repository.CoroutineRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import me.soushin.tinmvvm.base.BaseViewModel

/**
 * ================================================
 * Description:
 *
 * <p>
 * Created by TinMvvmTemplate on 03/06/2020 14:42
 * ================================================
 */
class CoroutineViewModel(application: Application) :
    BaseViewModel<CoroutineRepository>(application, CoroutineRepository()) {

    //这个 liveData 协程构造方法提供了一个协程代码块，这个块就是 LiveData 的作用域，
    // 当 LiveData 被观察的时候，里面的操作就会被执行，当 LiveData 不再被使用时，里面的操作就会取消。
    // 而且该协程构造方法产生的是一个不可变的 LiveData，可以直接暴露给对应的视图使用。而 emit() 方法则用来更新 LiveData 的数据。

    val result = liveData<String>(Dispatchers.IO) { emit(createString()) }

    private val itemId = MutableLiveData<String>()

    val result1 = itemId.switchMap {
        liveData { emit(fetchItem(it)) }
    }

    //使用 emitSource() 方法从另一个 LiveData 获取更新的结果:
    val result2 = liveData<String>(Dispatchers.IO) { emitSource(result1) }


    private fun fetchItem(it: String): String {
        ALog.i("fetchItem",it);
        return "item=${it}"
    }

    private fun createString():String {
        ALog.i("createString",Thread.currentThread().name);
        return DataUtils.buildRandom()
    }


    operator fun invoke(){
        ALog.i("调用invoke函数");
        //除此之外 使用 Lifecycle lifecycle-viewmodel-ktx 工件提供的 viewModelScope
        viewModelScope.launch(Dispatchers.IO) {

        }
    }


    fun onClick(): View.OnClickListener {
        return View.OnClickListener {
            when(it.id){
                R.id.btn_coroutine_fuc->{
                    //协程的普通用法
                    viewModelScope.launch {
                        val task1 = withContext(Dispatchers.IO) {
                            delay(2000)
                            ALog.e("task1执行完毕", Thread.currentThread().name);
                        }

                        val task2 = async(Dispatchers.IO) {
                           delay(2000)
                            ALog.e("task2执行完毕", Thread.currentThread().name);
                            return@async "task2"
                        }
                        val task3 = async(Dispatchers.IO) {
                            delay(2000)
                            ALog.e("task3执行完毕", Thread.currentThread().name);
                            return@async "task3"
                        }
                        ALog.e("所有任务都已执行", Thread.currentThread().name, task2.await(), task3.await());
                    }
                    viewModelScope.launch {
                        launch(Dispatchers.IO) {
                            delay(5000)
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
                    viewModelScope.launch(coroutineExceptionHandler){
                        throw RuntimeException("RuntimeException in nested coroutine")
                    }
                }
                R.id.btn_advanced_grammar->{
                    viewModelScope.launch {
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
                R.id.btn_result1->{
                    itemId.value = "${createString()},${itemId.value}"
                }
                R.id.btn_flow->{
                    mRepository.currentNumberFlow
                        .map { v ->  return@map v }
                        .filter { return@filter true }
                        .dropWhile { return@dropWhile true }
                        .flowOn(Dispatchers.IO)
                        .onCompletion {
                            ALog.i("onCompletion",this);
                        }
                }
            }
        }
    }


}
