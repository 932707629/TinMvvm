package me.soushin.tinmvvm.base

import android.app.Application
import androidx.lifecycle.*
import com.blankj.ALog
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import me.soushin.tinmvvm.config.AppComponent
import me.soushin.tinmvvm.rxerror.RxErrorHandler

/**
 * @author created by Soushin
 * @time 2020/1/7 16 38
 * 在viewModel中使用协程有已知有两种方法:
 * 1:使用lifecycle-viewmodel-ktx组件提供的[viewModelScope]
 * 2:使用LifecycleOwner提供的[lifecycleScope.lifecycleScope]
 */
open class BaseViewModel<R: BaseRepository>(application: Application,val mRepository: R) :
    AndroidViewModel(application), LifecycleEventObserver {

    protected var mCompositeDisposable : CompositeDisposable ?= null
    //生命周期
    var lifecycle:LifecycleOwner?=null

    /**
     *协程异常处理
     * 子类可以自行override实现自定义异常处理
     */
    protected var coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
//        println("Handle $exception in CoroutineExceptionHandler")
        getErrorHandler()?.mHandlerFactory?.handleError(exception)
    }

    protected fun getErrorHandler(): RxErrorHandler?{
        return AppComponent.rxErrorHandler
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        //Activity/Fragment 生命周期回调
        this.lifecycle = source
        when(event){
            Lifecycle.Event.ON_CREATE -> onCreate(source)
            Lifecycle.Event.ON_START -> onStart(source)
            Lifecycle.Event.ON_RESUME -> onResume(source)
            Lifecycle.Event.ON_PAUSE -> onPause(source)
            Lifecycle.Event.ON_STOP -> onStop(source)
            Lifecycle.Event.ON_DESTROY -> onDestroy(source)
            Lifecycle.Event.ON_ANY -> println("onStateChanged==$event")
        }
    }

    open fun onCreate(source: LifecycleOwner){}
    open fun onStart(source: LifecycleOwner){}
    open fun onResume(source: LifecycleOwner){}
    open fun onPause(source: LifecycleOwner){}
    open fun onStop(source: LifecycleOwner){}
    open fun onDestroy(source: LifecycleOwner){
        clearDisposable()//终端rxjava管道
        viewModelScope.cancel()//取消协程
        source.lifecycle.removeObserver(this)//取消生命周期订阅
        source.lifecycleScope.cancel()//取消协程
        mRepository.onDestroy()
        this.lifecycle=null
    }

    open fun addDispose(disposable: Disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable!!.add(disposable) //将所有 Disposable 放入容器集中处理
    }

    open fun getApp():Application{
        return getApplication()
    }

    /**
     * 直接用viewModelScope.launcher{}更舒适
     */
    open fun getLifecycleScope(): CoroutineScope? {
        return lifecycle?.lifecycleScope
    }

    /**
     * 在Lifecycle.Event.ON_DESTROY会置空
     * 注意使用的时机
     * 当ViewModel复用的时候，
     * lifecycle会出现为空的情况(中间会重复onCreate()、onDestroy()导致lifecycle置空)
     * 目前已经在DataBindingActivity/DataBindingFragment创建ViewModel时做了赋值处理
     */
    open fun getLifeCycleOwner():LifecycleOwner{
        return lifecycle!!
    }

    /**
     * 中断RxJava管道
     */
    open fun clearDisposable(){
        this.mCompositeDisposable?.clear()
    }

    /**
     * 中断某个管道
     */
    open fun removeDispose(disposable: Disposable){
        mCompositeDisposable?.remove(disposable)
    }




}