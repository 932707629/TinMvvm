package me.soushin.tinmvvm.base

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope


abstract class BaseService :Service(), CoroutineScope by MainScope() {

    protected var mCompositeDisposable: CompositeDisposable? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("onDestroy: ", "${mCompositeDisposable?.size()}")
        mCompositeDisposable?.clear();//保证 Service 结束时取消所有正在执行的订阅
        mCompositeDisposable=null
    }

    protected open fun addDispose(disposable: Disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable?.add(disposable) //将所有 Disposable 放入容器集中处理
    }

    /**
     * 初始化
     */
    abstract fun init()

}