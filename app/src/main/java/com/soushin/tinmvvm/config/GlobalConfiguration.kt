package com.soushin.tinmvvm.config

import android.app.Application
import android.content.Context
import com.soushin.tinmvvm.network.Api
import me.soushin.tinmvvm.base.BaseApp
import me.soushin.tinmvvm.config.GlobalConfigModule
import me.soushin.tinmvvm.listener.AppLifecycle
import me.soushin.tinmvvm.listener.ConfigModule
import okhttp3.OkHttpClient
import rxhttp.wrapper.cookie.CookieStore
import rxhttp.wrapper.ssl.HttpsUtils
import java.io.File
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier

/**
 * 全局配置
 * @auther SouShin
 * @time 2020/7/15 15:34
 */
class GlobalConfiguration :ConfigModule {

    override fun applyOptions(context: Context, builder: GlobalConfigModule.Builder) {
        builder
            .okHttpConfig(getOkHttpBuilder(context))
//            .globalHeaderMap()//公共请求头
//            .globalParamsMap()//公共请求参数
            .build()
    }

    private fun getOkHttpBuilder(context: Context): OkHttpClient.Builder {
        val file = File(context.externalCacheDir, "RxHttpCookie")
        val sslParams= HttpsUtils.getSslSocketFactory()
        return OkHttpClient.Builder()
            .callTimeout(Api.TIMEOUT,TimeUnit.SECONDS)
            .connectTimeout(Api.TIMEOUT,TimeUnit.SECONDS)
            .writeTimeout(Api.TIMEOUT,TimeUnit.SECONDS)
            .readTimeout(Api.TIMEOUT,TimeUnit.SECONDS)
            .cookieJar(CookieStore(file))
            .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager) //添加信任证书
            .hostnameVerifier(HostnameVerifier { hostname, session -> true })
        //忽略host验证
//            .followRedirects(false)  //禁制OkHttp的重定向操作，我们自己处理重定向
//            .addInterceptor(new RedirectInterceptor())
//            .addInterceptor(new TokenInterceptor())
    }

    override fun injectAppLifecycle(context: Context, lifecycles: MutableList<AppLifecycle>) {
        lifecycles.add(AppLifecycleImpl())
    }

    override fun injectActivityLifecycle(
        context: Context?,
        lifecycles: MutableList<Application.ActivityLifecycleCallbacks>
    ) {
        lifecycles.add(ActivityLifecycleCallbacksImpl())
    }
}