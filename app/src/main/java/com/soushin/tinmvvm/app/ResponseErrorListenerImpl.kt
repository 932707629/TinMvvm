package com.soushin.tinmvvm.app

import android.content.Context
import android.net.ParseException
import android.text.TextUtils
import com.blankj.ALog
import com.google.gson.JsonIOException
import com.google.gson.JsonParseException
import com.hjq.toast.ToastUtils
import me.soushin.tinmvvm.listener.ResponseErrorListener
import org.json.JSONException
import rxhttp.wrapper.exception.HttpStatusCodeException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 *
 * @auther SouShin
 * @time 2020/7/22 10:15
 */
class ResponseErrorListenerImpl : ResponseErrorListener {
    override fun handleResponseError(context: Context?, t: Throwable?) {
        ALog.i("Catch-Error",t?.message);
        //这里不光只能打印错误, 还可以根据不同的错误做出不同的逻辑处理
        //这里只是对几个常用错误进行简单的处理, 展示这个类的用法, 在实际开发中请您自行对更多错误进行更严谨的处理
        var msg = t?.message?:"未知错误"
        if (t is UnknownHostException) {
            msg = "网络不可用"
        } else if (t is SocketTimeoutException) {
            msg = "请求网络超时"
        } else if (t is HttpStatusCodeException) {
            msg = convertStatusCode(t)
        } else if (t is JsonParseException || t is ParseException || t is JSONException || t is JsonIOException) {
            msg = "数据解析错误"
        }
        ToastUtils.show(msg)
    }

    private fun convertStatusCode(httpException: HttpStatusCodeException): String {
        return when (httpException.statusCode) {
            500 -> {
                "服务器发生错误"
            }
            404 -> {
                "请求地址不存在"
            }
            403 -> {
                "请求被服务器拒绝"
            }
            307 -> {
                "请求被重定向到其他页面"
            }
            else -> {
                httpException.message?:"未知错误"
            }
        }
    }

}