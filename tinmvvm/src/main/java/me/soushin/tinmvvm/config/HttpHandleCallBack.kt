package me.soushin.tinmvvm.config

import androidx.lifecycle.LifecycleOwner
import com.rxjava.rxlife.RxLife
import com.rxjava.rxlife.Scope
import io.reactivex.disposables.Disposable
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import me.soushin.tinmvvm.utils.AppManager

/**
 * @author created by Soushin
 * @time 2020/1/17 14 32
 */
open class HttpHandleCallBack<T> :ErrorHandleSubscriber<T> {

    /**
     * lifecycleOwner 会添加生命周期订阅
     */
    constructor(lifecycleOwner: LifecycleOwner):super(AppComponent.rxErrorHandler){
        RxLife.`as`<Any>(lifecycleOwner)
    }

    /**
     * lifecycleOwner 会添加生命周期订阅
     */
    constructor():super(AppComponent.rxErrorHandler){
        AppManager.get().currentActivity?.let {
            RxLife.`as`<Any>(it as LifecycleOwner)
        }
    }

    override fun onSubscribe(d: Disposable) {

    }

    override fun onNext(result: T) {

    }

    override fun onError(t: Throwable) {
        super.onError(t)
    }


    override fun onComplete() {

    }

}