package me.soushin.tinmvvm.base

import android.app.Application
import androidx.lifecycle.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import me.soushin.tinmvvm.config.AppComponent

/**
 * @author created by Soushin
 * @time 2020/1/7 16 38
 */
open class BaseViewModel<M: BaseModel>(application: Application, val model: M) : AndroidViewModel(
    application
), CoroutineScope by MainScope(), LifecycleEventObserver {

    private val mCompositeDisposable by lazy { CompositeDisposable() }

//    private val job by lazy { Job() }
    //生命周期
    protected var lifecycle:LifecycleOwner?=null

    //子类可以自行override实现自定义异常处理
    open val coroutineExceptionHandler get() = CoroutineExceptionHandler { coroutineContext, exception ->
//        println("Handle $exception in CoroutineExceptionHandler")
        AppComponent.rxErrorHandler?.handlerFactory?.handleError(exception)
    }

    //注册生命周期 需要在网络请求的时候用到它
    fun registerLifecycleOwner(lifecycleOwner: LifecycleOwner){
        this.lifecycle= lifecycleOwner
    }

    fun addSubcribe(dis: Disposable){
        mCompositeDisposable.add(dis)
    }

    override fun onCleared() {
        super.onCleared()//会跟随页面生命周期销毁
        model.onCleared()
//        job.cancel()
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        //Activity/Fragment 生命周期回调
        if (event == Lifecycle.Event.ON_DESTROY) {  //Activity/Fragment 销毁
//            println("管道中断....")
            source.lifecycle.removeObserver(this)
            this.lifecycle=null
            dispose() //中断RxJava管道
        }
    }

    open fun dispose() {
        this.mCompositeDisposable.dispose()
    }

}