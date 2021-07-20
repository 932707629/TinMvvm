package me.soushin.tinmvvm.base

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.blankj.ALog
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope


abstract class BaseService :Service(), CoroutineScope by MainScope() {

    private val mCompositeDisposable by lazy { CompositeDisposable() }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        ALog.e("onDestroy: ", "${mCompositeDisposable.size()}")
        mCompositeDisposable.dispose();//保证 Service 结束时取消所有正在执行的订阅
    }

    protected open fun addDispose(disposable: Disposable) {
        mCompositeDisposable.add(disposable) //将所有 Disposable 放入容器集中处理
    }

    protected open fun removeDispose(disposable: Disposable){
        mCompositeDisposable.remove(disposable)
    }

    /**
     * 初始化
     */
    abstract fun init()

}