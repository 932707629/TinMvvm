package com.soushin.tinmvvm.widget

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @author created by Soushin
 * @time 2020/1/17 14 32
 */
interface HttpHandleCallBack<T> :Observer<T> {

    override fun onSubscribe(d: Disposable) {

    }

    override fun onNext(result: T) {

    }

    override fun onError(e: Throwable) {

    }

    override fun onComplete() {

    }

}