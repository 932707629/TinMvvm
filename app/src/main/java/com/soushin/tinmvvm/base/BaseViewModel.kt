package com.soushin.tinmvvm.base

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference

/**
 * @author created by Soushin
 * @time 2020/1/7 16 38
 */
open class BaseViewModel<M:BaseModel> : AndroidViewModel {

    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()
    var model:M?=null
    var weakReference: WeakReference<LifecycleOwner>?=null

    constructor(application:Application, @NonNull model:M):super(application){
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
        model?.injectLifecycleOwner(lifecycleOwner)
    }

    override fun onCleared() {
        super.onCleared()//会跟随页面生命周期销毁
        mCompositeDisposable.clear()
    }

}