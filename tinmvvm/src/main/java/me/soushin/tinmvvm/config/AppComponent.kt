package me.soushin.tinmvvm.config

import me.soushin.tinmvvm.base.BaseApp
import okhttp3.OkHttpClient
import rxhttp.wrapper.cookie.CookieStore
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.ssl.HttpsUtils
import java.io.File
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

/**
 * 拥有此类即可调用对应的方法拿到对应实例
 * @auther SouShin
 * @time 2020/7/15 16:37
 */
class AppComponent {

    val globalConfig: GlobalConfigModule

    constructor(globalConfig: GlobalConfigModule){
        this.globalConfig=globalConfig
        // TODO: 2020/7/15  这里拿到全局配置信息即可实现对全局参数统一配置
        initRxHttp(globalConfig)
    }

    private fun initRxHttp(globalConfig: GlobalConfigModule) {
        //如果没有配置okhttp就用默认的
        if (globalConfig.okhttpBuilder==null){
            val file = File(BaseApp.instance?.externalCacheDir, "RxHttpCookie")
            val sslParams= HttpsUtils.getSslSocketFactory()
            globalConfig.okhttpBuilder= OkHttpClient.Builder()
                .cookieJar(CookieStore(file))
                .callTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager) //添加信任证书
                .hostnameVerifier(HostnameVerifier { hostname, session -> true })
        }
        RxHttp.init(globalConfig.okhttpBuilder!!.build())
        //添加公共请求参数、请求头
        RxHttp.setOnParamAssembly {
            if (!globalConfig.globalHeaderMap.isNullOrEmpty()){
                it.addAll(globalConfig.globalHeaderMap)
            }
            if (!globalConfig.globalParamsMap.isNullOrEmpty()){
                it.addAllHeader(globalConfig.globalParamsMap)
            }
            return@setOnParamAssembly it
        }
    }


}