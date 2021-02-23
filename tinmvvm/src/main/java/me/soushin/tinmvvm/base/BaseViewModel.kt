package me.soushin.tinmvvm.base

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.rxjava.rxlife.Scope
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.lang.ref.WeakReference
import kotlin.coroutines.CoroutineContext

/**
 * 因为ViewModel拥有远比Activity和Fragment还要长的生命周期，
 * 所以ViewModel中最好不要持有Activity或者Fragment的引用，
 * 否则很容易引起内存泄漏。
 *
 * @author created by Soushin
 * @time 2020/1/7 16 38
 */
open class BaseViewModel<M: BaseModel>(application: Application, val model: M) : AndroidViewModel(
    application
), CoroutineScope, Scope,
    LifecycleEventObserver {

    private var mCompositeDisposable: CompositeDisposable?=null

    private val job = Job()

    //这里可以让basemodel具有协程的功能
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    fun addSubcribe(dis: Disposable){
        if (mCompositeDisposable==null){
            this.mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable?.add(dis)
    }

    override fun onCleared() {
        super.onCleared()//会跟随页面生命周期销毁
        job.cancel()
    }

    override fun onScopeEnd() {

    }

    override fun onScopeStart(d: Disposable) {
        addSubcribe(d)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        //Activity/Fragment 生命周期回调
        if (event == Lifecycle.Event.ON_DESTROY) {  //Activity/Fragment 销毁
//            println("管道中断....")
            source.lifecycle.removeObserver(this)
            dispose() //中断RxJava管道
        }
    }

    open fun dispose() {
        this.mCompositeDisposable?.dispose()
    }

}