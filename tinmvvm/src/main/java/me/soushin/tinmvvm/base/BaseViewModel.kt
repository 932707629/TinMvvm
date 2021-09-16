package me.soushin.tinmvvm.base

import android.app.Application
import androidx.lifecycle.*
import com.blankj.ALog
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
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

    open fun addDispose(disposable: Disposable) {
        mCompositeDisposable.add(disposable) //将所有 Disposable 放入容器集中处理
    }

    open fun removeDispose(disposable: Disposable){
        mCompositeDisposable.remove(disposable)
    }

    open fun getApp():Application{
        return getApplication()
    }

    open fun getCoroutineScope(): CoroutineScope {
        return viewModelScope
    }

    /**
     * 在Lifecycle.Event.ON_DESTROY会置空
     * 注意使用的时机
     */
    open fun getLifeCycleOwner():LifecycleOwner{
        return lifecycle!!
    }

    override fun onCleared() {
        super.onCleared()//会跟随页面生命周期销毁
        mRepository.onCleared()
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        //Activity/Fragment 生命周期回调
        ALog.i("onStateChanged",source.javaClass.simpleName,event);
        this.lifecycle = source
        if (event == Lifecycle.Event.ON_DESTROY) {//Activity/Fragment 销毁
            clearDisposable()
            source.lifecycle.removeObserver(this)
            this.lifecycle=null
        }
    }

    //中断RxJava管道
    open fun clearDisposable(){
        this.mCompositeDisposable.clear()
    }

}