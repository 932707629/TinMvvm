package com.soushin.tinmvvm.app

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.gson.JsonSyntaxException
import com.hjq.toast.ToastUtils
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import me.soushin.tinmvvm.base.DataBindingFragment
import me.soushin.tinmvvm.rxerror.RxErrorHandler
import rxhttp.wrapper.exception.HttpStatusCodeException
import rxhttp.wrapper.exception.ParseException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

/**
 * 扩展方法
 * @author created by Soushin
 * @time 2020/1/8 11 12
 */

fun showToasty(msg:Any?){
    ToastUtils.show(msg)
}

fun AppCompatActivity.go(clazz:Class<*>){
    startActivity(Intent(getThis(),clazz))
}

fun AppCompatActivity.getThis(): AppCompatActivity {
    return this
}

fun Fragment.getThis(): Fragment {
    return this
}

fun DataBindingFragment<*, *>.getCtx():Context{
    return this.mContext!!
}

fun DataBindingFragment<*, *>.go(clazz:Class<*>){
    startActivity(Intent(mContext,clazz))
}


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

