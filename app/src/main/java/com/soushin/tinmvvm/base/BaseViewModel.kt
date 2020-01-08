package com.soushin.tinmvvm.base

import androidx.lifecycle.ViewModel
import com.soushin.tinmvvm.utils.AppManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author created by Soushin
 * @time 2020/1/7 16 38
 */
open class BaseViewModel<M:BaseModel> : ViewModel {

    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()
    var model:M?=null

    constructor(model:M){
        this.model=model
    }

    fun addSubcribe(dis: Disposable){
        mCompositeDisposable.add(dis)
    }

    fun cancel(){
        mCompositeDisposable.clear()
    }

}