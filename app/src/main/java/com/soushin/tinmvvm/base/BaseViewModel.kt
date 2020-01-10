package com.soushin.tinmvvm.base

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.soushin.tinmvvm.utils.AppManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author created by Soushin
 * @time 2020/1/7 16 38
 */
open class BaseViewModel<M:BaseModel> : AndroidViewModel {

    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()
    var model:M?=null

    constructor(application:Application,model:M):super(application){
        this.model=model
    }

    fun addSubcribe(dis: Disposable){
        mCompositeDisposable.add(dis)
    }

    fun cancel(){
        mCompositeDisposable.clear()
    }

}