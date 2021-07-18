package me.soushin.tinmvvm.rxerror.handler

import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.rxjava.rxlife.RxLife
import com.rxjava.rxlife.Scope
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import me.soushin.tinmvvm.rxerror.RxErrorHandler
import me.soushin.tinmvvm.utils.AppManager

open class ErrorHandleSubscriber<T> : Observer<T> {
    private var mErrorHandler: RxErrorHandler?=null
    /**
     * lifecycleOwner 会添加生命周期订阅
     */
    constructor(lifecycleOwner: LifecycleOwner,mErrorHandler: RxErrorHandler?){
        this.mErrorHandler=mErrorHandler
        RxLife.to<Any>(lifecycleOwner)
    }

    constructor(scope: Scope,mErrorHandler: RxErrorHandler?){
        this.mErrorHandler=mErrorHandler
        RxLife.to<Any>(scope)
    }

    constructor(view: View,mErrorHandler: RxErrorHandler?){
        this.mErrorHandler=mErrorHandler
        RxLife.to<Any>(view)
    }

    /**
     * lifecycleOwner 会添加生命周期订阅
     */
    constructor(mErrorHandler: RxErrorHandler?){
        this.mErrorHandler=mErrorHandler
        if (AppManager.get().currentActivity is LifecycleOwner){
            RxLife.to<Any>(AppManager.get().currentActivity as LifecycleOwner)
        }
    }

    override fun onSubscribe(d: Disposable?) {}

    override fun onNext(result: T) {}

    override fun onComplete() {}

    override fun onError(e: Throwable?) {
        e?.printStackTrace()
        //如果你某个地方不想使用全局错误处理,则重写 onError(Throwable) 并将 super.onError(e); 删掉
        //如果你不仅想使用全局错误处理,还想加入自己的逻辑,则重写 onError(Throwable) 并在 super.onError(e); 后面加入自己的逻辑
        mErrorHandler?.mHandlerFactory?.handleError(e)
    }

}