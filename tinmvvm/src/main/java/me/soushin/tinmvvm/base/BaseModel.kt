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
open class BaseModel {

    var weakReference: WeakReference<LifecycleOwner>? = null

    /**
     * 生命周期注入
     */
    fun injectLifecycleOwner(@NonNull lifecycleOwner: LifecycleOwner) {
        this.weakReference = WeakReference(lifecycleOwner)
    }


}