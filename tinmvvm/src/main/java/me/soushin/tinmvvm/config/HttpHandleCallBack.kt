package me.soushin.tinmvvm.config

import io.reactivex.disposables.Disposable
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber

/**
 * @author created by Soushin
 * @time 2020/1/17 14 32
 */
open class HttpHandleCallBack<T> :ErrorHandleSubscriber<T> {

    constructor():super(AppComponent.rxErrorHandler){

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