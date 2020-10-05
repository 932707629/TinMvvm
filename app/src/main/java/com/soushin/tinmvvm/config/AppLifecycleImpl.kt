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
        initRxhttp(application)
    }

    override fun onTerminate(application: Application) {

    }

    private fun initRxhttp(context: Context){
        val file = File(context.externalCacheDir, "RxHttpCookie")
        val sslParams= HttpsUtils.getSslSocketFactory()
        val okHttpClient= OkHttpClient.Builder()
            .callTimeout(Api.TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(Api.TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Api.TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Api.TIMEOUT, TimeUnit.SECONDS)
            .cookieJar(CookieStore(file))
            .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager) //添加信任证书
            .hostnameVerifier(HostnameVerifier { hostname, session -> true }).build()
        //忽略host验证
//            .followRedirects(false)  //禁制OkHttp的重定向操作，我们自己处理重定向
//            .addInterceptor(new RedirectInterceptor())
//            .addInterceptor(new TokenInterceptor())

        RxHttp.init(okHttpClient,BuildConfig.DEBUG)
    }

}