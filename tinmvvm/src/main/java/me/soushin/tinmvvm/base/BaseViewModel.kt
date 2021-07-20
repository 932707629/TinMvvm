package me.soushin.tinmvvm.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import me.soushin.tinmvvm.config.AppComponent
import me.soushin.tinmvvm.rxerror.RxErrorHandler

/**
 * @author created by Soushin
 * @time 2020/1/7 16 38
 */
open class BaseViewModel<R: BaseRepository>(application: Application,val mRepository: R) :
    AndroidViewModel(application), LifecycleEventObserver {

    private val mCompositeDisposable by lazy { CompositeDisposable() }

    //生命周期
    protected var lifecycle:LifecycleOwner?=null

    //子类可以自行override实现自定义异常处理
    open val coroutineExceptionHandler get() = CoroutineExceptionHandler { coroutineContext, exception ->
//        println("Handle $exception in CoroutineExceptionHandler")
        getErrorHandler()?.mHandlerFactory?.handleError(exception)
    }

    protected fun getErrorHandler(): RxErrorHandler?{
        return AppComponent.rxErrorHandler
    }

    //注册生命周期 需要在网络请求的时候用到它
    fun registerLifecycleOwner(lifecycleOwner: LifecycleOwner){
        this.lifecycle= lifecycleOwner
    }

    protected open fun addDispose(disposable: Disposable) {
        mCompositeDisposable.add(disposable) //将所有 Disposable 放入容器集中处理
    }

    protected open fun removeDispose(disposable: Disposable){
        mCompositeDisposable.remove(disposable)
    }

    override fun onCleared() {
        super.onCleared()//会跟随页面生命周期销毁
        mRepository.onCleared()
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        //Activity/Fragment 生命周期回调
        if (event == Lifecycle.Event.ON_DESTROY) {  //Activity/Fragment 销毁
//            println("管道中断....")
            source.lifecycle.removeObserver(this)
            this.lifecycle=null
            this.mCompositeDisposable.dispose()//中断RxJava管道
        }
    }

}