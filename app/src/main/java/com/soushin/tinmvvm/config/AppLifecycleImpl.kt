package com.soushin.tinmvvm.config

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.hjq.toast.ToastUtils
import com.soushin.tinmvvm.BuildConfig
import com.soushin.tinmvvm.network.Api
import com.soushin.tinmvvm.widget.ToastStyle
import me.soushin.tinmvvm.listener.AppLifecycle
import okhttp3.OkHttpClient
import rxhttp.wrapper.cookie.CookieStore
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.ssl.HttpsUtils
import java.io.File
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier

/**
 * 功能相当于application
 * @auther SouShin
 * @time 2020/7/15 15:40
 */
class AppLifecycleImpl :AppLifecycle {

    override fun attachBaseContext(base: Context) {
        MultiDex.install(base)
    }

    override fun onCreate(application: Application) {
        ToastUtils.init(application, ToastStyle())
    }

    override fun onTerminate(application: Application) {

    }


}