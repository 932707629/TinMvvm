package com.soushin.tinmvvm.config

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.blankj.ALog
import com.hjq.toast.ToastUtils
import com.soushin.tinmvvm.BuildConfig
import com.soushin.tinmvvm.network.Api
import com.soushin.tinmvvm.widget.ToastStyle
import me.soushin.tinmvvm.listener.AppLifecycle
import okhttp3.OkHttpClient
import rxhttp.wrapper.cookie.CookieStore
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.ssl.SSLSocketFactoryImpl
import rxhttp.wrapper.ssl.X509TrustManagerImpl
import java.io.File
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLSession
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

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
        initALog(application)
        ToastUtils.init(application, ToastStyle())
        initRxHttp(application)
    }

    override fun onTerminate(application: Application) {

    }

    private fun initRxHttp(app:Application) {
        val file = File(app.externalCacheDir, "RxHttpCookie")
        val trustAllCert: X509TrustManager = X509TrustManagerImpl()
        val sslSocketFactory: SSLSocketFactory =
            SSLSocketFactoryImpl(trustAllCert)
        val client = OkHttpClient.Builder()
            .cookieJar(CookieStore(file))
            .connectTimeout(Api.TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Api.TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Api.TIMEOUT, TimeUnit.SECONDS)
            .sslSocketFactory(sslSocketFactory, trustAllCert) //添加信任证书
            .hostnameVerifier { hostname: String?, session: SSLSession? -> true } //忽略host验证
//            .followRedirects(false)  //禁制OkHttp的重定向操作，我们自己处理重定向
//            .addInterceptor(new RedirectInterceptor())
//            .addInterceptor(new TokenInterceptor())
            .build()
        //RxHttp初始化，自定义OkHttpClient对象，非必须
        //RxHttp初始化，自定义OkHttpClient对象，非必须
        RxHttp.init(client, BuildConfig.DEBUG)

        //设置公共参数，非必须
        //设置缓存策略，非必须
//        File file = new File(context.getExternalCacheDir(), "RxHttpCache");
//        RxHttpPlugins.setCache(file, 1000 * 100, CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);
//        RxHttpPlugins.setExcludeCacheKeys("time"); //设置一些key，不参与cacheKey的组拼
//设置数据解密/解码器，非必须
//        RxHttp.setResultDecoder(s -> s);
//设置全局的转换器，非必须
//        RxHttp.setConverter(FastJsonConverter.create())

//设置公共参数，非必须
        /*RxHttp.setOnParamAssembly { p: Param<*>? ->
            //根据不同请求添加不同参数，子线程执行，每次发送请求前都会被回调 如果希望部分请求不回调这里，发请求前调用Param.setAssemblyEnabled(false)即可
            val method = p!!.method
            if (method!!.isGet) { //Get请求

            } else if (method.isPost) { //Post请求

            }
            p.add("versionName", "1.0.0") //添加公共参数
                .add("time", System.currentTimeMillis())
                .addHeader("deviceType", "android") //添加公共请求头
        }*/
    }


    fun initALog(app: Application) {
        ALog.init(app)
            .setLogSwitch(BuildConfig.DEBUG) // 设置log总开关，包括输出到控制台和文件，默认开
            .setConsoleSwitch(BuildConfig.DEBUG) // 设置是否输出到控制台开关，默认开
            .setGlobalTag(null) // 设置log全局标签，默认为空
            // 当全局标签不为空时，我们输出的log全部为该tag，
            // 为空时，如果传入的tag为空那就显示类名，否则显示tag
            .setLogHeadSwitch(true) // 设置log头信息开关，默认为开
            .setLog2FileSwitch(false) // 打印log时是否存到文件的开关，默认关
            .setDir("") // 当自定义路径为空时，写入应用的/cache/log/目录中
            .setFilePrefix("") // 当文件前缀为空时，默认为"alog"，即写入文件为"alog-MM-dd.txt"
            .setBorderSwitch(false) // 输出日志是否带边框开关，默认开
            .setConsoleFilter(ALog.V) // log的控制台过滤器，和logcat过滤器同理，默认Verbose
            .setFileFilter(ALog.V).stackDeep = 1 // log栈深度，默认为1
    }


}