package me.soushin.tinmvvm.base

import android.app.Application
import androidx.lifecycle.*
import com.rxjava.rxlife.LifecycleScope
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.CoroutineExceptionHandler
import me.soushin.tinmvvm.config.AppComponent
import me.soushin.tinmvvm.rxerror.RxErrorHandler

/**
 * @author created by Soushin
 * @time 2020/1/7 16 38
 * 在viewModel中使用协程有已知有两种方法:
 * 1:使用LifecycleOwner提供的[lifecycleScope.lifecycleScope]
 * 2:使用lifecycle-viewmodel-ktx组件提供的[viewModelScope]
 */
open class BaseViewModel<R: BaseRepository>(application: Application,val mRepository: R) :
    AndroidViewModel(application), LifecycleEventObserver {

    private val mCompositeDisposable by lazy { CompositeDisposable() }
    //生命周期
    private var lifecycle:LifecycleOwner?=null

    //子类可以自行override实现自定义异常处理
    open val coroutineExceptionHandler get() = CoroutineExceptionHandler { coroutineContext, exception ->
//        println("Handle $exception in CoroutineExceptionHandler")
        getErrorHandler()?.mHandlerFactory?.handleError(exception)
    }

    protected fun getErrorHandler(): RxErrorHandler?{
        return AppComponent.rxErrorHandler
    }

    //注册生命周期 可以在网络请求的时候用到它
    open fun registerLifecycleOwner(lifecycleOwner: LifecycleOwner){
        this.lifecycle= lifecycleOwner
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

    open fun getLifecycleOwner():LifecycleOwner{
        return lifecycle!!
    }

    open fun getLifecycleScope():LifecycleCoroutineScope{
        return getLifecycleOwner().lifecycleScope
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