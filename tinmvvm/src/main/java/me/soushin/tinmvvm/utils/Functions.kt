package me.soushin.tinmvvm.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import java.lang.reflect.ParameterizedType

@JvmName("inflateWithGeneric")
fun <VD : ViewDataBinding> Any.inflateBindingWithGeneric(layoutInflater: LayoutInflater): VD =
    withGenericBindingClass(this) { clazz ->
        clazz.getMethod("inflate", LayoutInflater::class.java).invoke(null, layoutInflater) as VD
    }

@JvmName("inflateWithGeneric")
fun <VD : ViewDataBinding> Any.inflateBindingWithGeneric(layoutInflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean): VD =
    withGenericBindingClass(this) { clazz ->
        clazz.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
            .invoke(null, layoutInflater, parent, attachToParent) as VD
    }

@JvmName("inflateWithGeneric")
fun <VD : ViewDataBinding> Any.inflateBindingWithGeneric(parent: ViewGroup): VD =
    inflateBindingWithGeneric(LayoutInflater.from(parent.context), parent, false)

fun <VD : ViewDataBinding> Any.bindViewWithGeneric(view: View): VD =
    withGenericBindingClass(this) { clazz ->
        clazz.getMethod("bind", LayoutInflater::class.java).invoke(null, view) as VD
    }

private fun <VD : ViewDataBinding> withGenericBindingClass(any: Any, block: (Class<VD>) -> VD): VD {
    any.allParameterizedType.forEach { parameterizedType ->
        parameterizedType.actualTypeArguments.forEach {
            try {
                return block.invoke(it as Class<VD>)
            } catch (e: NoSuchMethodException) {
            }
        }
    }
    throw IllegalArgumentException("There is no generic of ViewBinding.")
}

private val Any.allParameterizedType: List<ParameterizedType>
    get() {
        val genericParameterizedType = mutableListOf<ParameterizedType>()
        var genericSuperclass = javaClass.genericSuperclass
        var superclass = javaClass.superclass
        while (superclass != null) {
            if (genericSuperclass is ParameterizedType) {
                genericParameterizedType.add(genericSuperclass)
            }
            genericSuperclass = superclass.genericSuperclass
            superclass = superclass.superclass
        }
        return genericParameterizedType
    }


fun throttleClick(wait: Long = 200, block: ((View) -> Unit)): View.OnClickListener {
    return View.OnClickListener { v ->
        val current = System.currentTimeMillis()
        val lastClickTime = (v.getTag(v.id) as? Long) ?: 0
        if (current - lastClickTime > wait) {
            v.setTag(v.id, current)
            block(v)
        }
    }
}

fun debounceClick(wait: Long = 200, block: ((View) -> Unit)): View.OnClickListener {
    return View.OnClickListener { v ->
        var action = (v.getTag(v.id) as? DebounceAction)
        if(action == null){
            action = DebounceAction(v, block)
            v.setTag(v.id, action)
        }else{
            action.block = block
        }
        v.removeCallbacks(action)
        v.postDelayed(action, wait)
    }
}

class DebounceAction(val view: View, var block: ((View) -> Unit)): Runnable {
    override fun run() {
        if(view.isAttachedToWindow){
            block(view)
        }
    }
}

fun View.onClick(wait: Long = 200, block: ((View) -> Unit)) {
    setOnClickListener(throttleClick(wait, block))
}

fun View.onDebounceClick(wait: Long = 200, block: ((View) -> Unit)) {
    setOnClickListener(debounceClick(wait, block))
}









