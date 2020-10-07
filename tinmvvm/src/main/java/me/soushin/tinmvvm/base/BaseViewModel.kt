package me.soushin.tinmvvm.base

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.lang.ref.WeakReference
import kotlin.coroutines.CoroutineContext

/**
 * @author created by Soushin
 * @time 2020/1/7 16 38
 */
open class BaseViewModel<M: BaseModel> : AndroidViewModel, CoroutineScope {

    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()
    val model:M
    var weakReference: WeakReference<LifecycleOwner>?=null

    //这里可以让basemodel具有协程的功能
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    var job = Job()

    constructor(application:Application, model:M):super(application){
        this.model=model
    }

    fun addSubcribe(dis: Disposable){
        mCompositeDisposable.add(dis)
    }

    /**
     * 生命周期注入
     */
    fun injectLifecycleOwner(@NonNull lifecycleOwner: LifecycleOwner){
        this.weakReference= WeakReference(lifecycleOwner)
        model.injectLifecycleOwner(lifecycleOwner)
    }

    override fun onCleared() {
        super.onCleared()//会跟随页面生命周期销毁
        mCompositeDisposable.clear()
        job.cancel()
    }

}