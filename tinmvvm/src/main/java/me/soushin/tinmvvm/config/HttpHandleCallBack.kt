package me.soushin.tinmvvm.config

import android.view.View
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

    constructor(scope: Scope):super(AppComponent.rxErrorHandler){
        RxLife.`as`<Any>(scope)
    }

    constructor(view: View):super(AppComponent.rxErrorHandler){
        RxLife.`as`<Any>(view)
    }

    /**
     * lifecycleOwner 会添加生命周期订阅
     */
    constructor():super(AppComponent.rxErrorHandler){
        if (AppManager.get().currentActivity is LifecycleOwner){
            RxLife.`as`<Any>(AppManager.get().currentActivity as LifecycleOwner)
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