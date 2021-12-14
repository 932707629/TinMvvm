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

fun AppCompatActivity.showToasty(msg:Any?){
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

fun DataBindingFragment<*, *>.showToasty(msg:Any?){
    ToastUtils.show(msg)
}

fun DataBindingFragment<*, *>.go(clazz:Class<*>){
    startActivity(Intent(mContext,clazz))
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

///在异常回调中便可拿到code及msg字段，需要注意的是，
// it.code及it.msg是我为Throwable类扩展的两个属性
val Throwable.code: Int
    get() {
        val errorCode = when (this) {
            is HttpStatusCodeException -> "${this.statusCode}" //Http状态码异常
            is ParseException -> this.errorCode     //业务code异常
            else -> "-1"
        }
        return try {
            errorCode.toInt()
        } catch (e: Exception) {
            -1
        }
    }

val Throwable.msg: String
    get() {
        return if (this is UnknownHostException) { //网络异常
            "当前无网络，请检查你的网络设置"
        } else if (
            this is SocketTimeoutException  //okhttp全局设置超时
            || this is TimeoutException     //rxjava中的timeout方法超时
            || this is TimeoutCancellationException  //协程超时
        ) {
            "连接超时,请稍后再试"
        } else if (this is ConnectException) {
            "网络不给力，请稍候重试！"
        } else if (this is HttpStatusCodeException) {               //请求失败异常
            "Http状态码异常"
        } else if (this is JsonSyntaxException) {  //请求成功，但Json语法异常,导致解析失败
            "数据解析失败,请检查数据是否正确"
        } else if (this is ParseException) {       // ParseException异常表明请求成功，但是数据不正确
            this.message ?: errorCode   //msg为空，显示code
        } else {
            "请求失败，请稍后再试"
        }
    }


