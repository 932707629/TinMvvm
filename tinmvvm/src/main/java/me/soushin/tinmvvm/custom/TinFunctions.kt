package me.soushin.tinmvvm.custom

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.annotation.DrawableRes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import me.soushin.tinmvvm.base.DataBindingFragment
import me.soushin.tinmvvm.rxerror.RxErrorHandler

fun throttleClick(wait: Long = 1000, block: ((View) -> Unit)): View.OnClickListener {
    return View.OnClickListener { v ->
        val current = System.currentTimeMillis()
        val lastClickTime = (v.getTag(v.id) as? Long) ?: 0
        if (current - lastClickTime > wait) {
            v.setTag(v.id, current)
            block(v)
        }
    }
}

fun debounceClick(wait: Long = 1000, block: ((View) -> Unit)): View.OnClickListener {
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

fun View.onClick(wait: Long = 1000, block: ((View) -> Unit)) {
    setOnClickListener(throttleClick(wait, block))
}

fun View.onDebounceClick(wait: Long = 1000, block: ((View) -> Unit)) {
    setOnClickListener(debounceClick(wait, block))
}


/**
 * 扩展视图可见性
 */
fun View.setVisible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

/**
 * 扩展视图可见性
 */
fun View.setInVisible(inVisible: Boolean) {
    this.visibility = if (inVisible) View.INVISIBLE else View.VISIBLE
}

fun DataBindingFragment<*, *>.go(clazz:Class<*>,finish:Boolean = false){
    startActivity(Intent(mContext,clazz))
    if (finish) requireActivity().finish()
}

fun TextView.drawableStart(@DrawableRes drawable: Int) {
    this.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, 0, 0, 0)
}

fun TextView.drawableTop(@DrawableRes drawable: Int) {
    this.setCompoundDrawablesRelativeWithIntrinsicBounds(0, drawable, 0, 0)
}

fun TextView.drawableEnd(@DrawableRes drawable: Int) {
    this.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, drawable, 0)
}

fun TextView.drawableBottom(@DrawableRes drawable: Int) {
    this.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, drawable)
}

/**
 * 适用于awaitResult
 */
fun Result<*>.onFailure(rxErrorHandler: RxErrorHandler?):Result<*>{
    return this.onFailure {
        rxErrorHandler?.mHandlerFactory?.handleError(it)
    }
}


/**
 * 适用于flow
 */
fun <T> Flow<T>.catch(rxErrorHandler: RxErrorHandler?): Flow<T> {
    return this.catch {
        rxErrorHandler?.mHandlerFactory?.handleError(it)
    }
}



