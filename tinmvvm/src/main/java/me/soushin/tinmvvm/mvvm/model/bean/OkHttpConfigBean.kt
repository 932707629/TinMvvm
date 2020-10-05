package me.soushin.tinmvvm.mvvm.model.bean

import me.soushin.tinmvvm.utils.HttpsUtils
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

/**
 * okhttpclient配置类
 * @auther SouShin
 * @time 2020/10/4 11:56
 */
data class OkHttpConfigBean(
    val callTimeout:Long=30*1000,
    val connectTimeout:Long=30*1000,
    val readTimeout:Long=30*1000,
    val writeTimeout:Long=30*1000,
    var sslSocketFactory: SSLSocketFactory?=null,
    var trustManager: X509TrustManager?=null,
    var hostnameVerifier: HostnameVerifier?=null
) {

    init {
        val sslParams= HttpsUtils.getSslSocketFactory()
        sslSocketFactory=sslParams.sSLSocketFactory
        trustManager=sslParams.trustManager
        hostnameVerifier=HostnameVerifier { _, _ -> true }
    }

    override fun toString(): String {
        return "OkHttpConfigBean(callTimeout=$callTimeout, connectTimeout=$connectTimeout," +
                " readTimeout=$readTimeout, writeTimeout=$writeTimeout," +
                " sslSocketFactory=$sslSocketFactory, trustManager=$trustManager, " +
                "hostnameVerifier=$hostnameVerifier)"
    }


}
