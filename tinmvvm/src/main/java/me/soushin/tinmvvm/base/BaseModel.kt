package me.soushin.tinmvvm.base

import androidx.annotation.NonNull
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.lang.ref.WeakReference
import kotlin.coroutines.CoroutineContext


/**
 * @author created by Soushin
 * @time 2020/1/8 09 32
 */
open class BaseModel : CoroutineScope {

    var weakReference: WeakReference<LifecycleOwner>? = null

    //这里可以让basemodel具有协程的功能

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    var job = Job()

    /**
     * 生命周期注入
     */
    fun injectLifecycleOwner(@NonNull lifecycleOwner: LifecycleOwner) {
        this.weakReference = WeakReference(lifecycleOwner)
    }


}