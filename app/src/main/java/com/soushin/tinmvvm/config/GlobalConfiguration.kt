package com.soushin.tinmvvm.config

import android.app.Activity
import android.app.Application
import android.content.Context
import com.blankj.ALog
import com.soushin.tinmvvm.BuildConfig
import com.soushin.tinmvvm.network.Api
import me.soushin.tinmvvm.config.GlobalConfigModule
import me.soushin.tinmvvm.listener.AppLifecycle
import me.soushin.tinmvvm.listener.ConfigModule
import okhttp3.OkHttpClient
import rxhttp.wrapper.cookie.CookieStore
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.ssl.HttpsUtils
import java.io.File
import java.util.concurrent.TimeUnit
/**
 * 全局配置
 * @auther SouShin
 * @time 2020/7/15 15:34
 */
class GlobalConfiguration :ConfigModule {

    override fun applyOptions(context: Context, builder: GlobalConfigModule.Builder) {
        builder.setOkHttpClient(initRxhttp(context))
            .errorResponseListener(ResponseErrorListenerImpl())
//            .logDir("")//crash日志输出文件夹
            .logEnable(BuildConfig.DEBUG)
            .build()
    }


    private fun initRxhttp(context: Context):OkHttpClient{
        val file = File(context.externalCacheDir, "RxHttpCookie")
        val sslParams= HttpsUtils.getSslSocketFactory()
        val okHttpClient= OkHttpClient.Builder()
            .cookieJar(CookieStore(File(context.externalCacheDir,"TinmvvmCookie")))
            .callTimeout(Api.TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(Api.TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Api.TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Api.TIMEOUT, TimeUnit.SECONDS)
            .cookieJar(CookieStore(file))
            .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager) //添加信任证书
            .hostnameVerifier { _, _ -> true }.build()
        //忽略host验证
//            .followRedirects(false)  //禁制OkHttp的重定向操作，我们自己处理重定向
//            .addInterceptor(new RedirectInterceptor())
//            .addInterceptor(new TokenInterceptor())
        RxHttp.init(okHttpClient,BuildConfig.DEBUG)
        return okHttpClient
    }

    override fun injectAppLifecycle(context: Context, lifecycles: MutableList<AppLifecycle>) {
        lifecycles.add(AppLifecycleImpl())
    }

    override fun injectActivityLifecycle(context: Context?, lifecycles: MutableList<Application.ActivityLifecycleCallbacks>) {
        lifecycles.add(ActivityLifecycleCallbacksImpl())
    }
}