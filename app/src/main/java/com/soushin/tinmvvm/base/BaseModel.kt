package com.soushin.tinmvvm.base

import androidx.annotation.NonNull
import androidx.lifecycle.LifecycleOwner
import java.lang.ref.WeakReference


/**
 * @author created by Soushin
 * @time 2020/1/8 09 32
 */
open class BaseModel {

    var weakReference: WeakReference<LifecycleOwner>?=null

    /**
     * 生命周期注入
     */
    fun injectLifecycleOwner(@NonNull lifecycleOwner: LifecycleOwner){
        this.weakReference= WeakReference(lifecycleOwner)
    }

}