package com.soushin.tinmvvm.app

import android.app.Application
import android.content.Context
import com.soushin.tinmvvm.BuildConfig
import com.soushin.tinmvvm.app.network.Api
import me.soushin.tinmvvm.config.GlobalConfigModule
import me.soushin.tinmvvm.listener.AppLifecycle
import me.soushin.tinmvvm.listener.ConfigModule
import okhttp3.OkHttpClient
import rxhttp.RxHttpPlugins
import rxhttp.wrapper.cookie.CookieStore
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
        builder.setOkHttpClient(initOkhttp(context))
            .errorResponseListener(ResponseErrorListenerImpl())
//            .logDir("")//crash日志输出文件夹
            .setDebug(BuildConfig.DEBUG)
            .build()
    }

    //配置OkHttp
    private fun initOkhttp(context: Context):OkHttpClient{
        val file = File(context.externalCacheDir, "OkHttpCookie")
        val sslParams= HttpsUtils.getSslSocketFactory()
        return OkHttpClient.Builder()
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
    }

    override fun injectAppLifecycle(context: Context, lifecycles: MutableList<AppLifecycle>) {
        lifecycles.add(AppLifecycleImpl())
    }

    override fun injectActivityLifecycle(context: Context?, lifecycles: MutableList<Application.ActivityLifecycleCallbacks>) {
        lifecycles.add(ActivityLifecycleCallbacksImpl())
    }
}