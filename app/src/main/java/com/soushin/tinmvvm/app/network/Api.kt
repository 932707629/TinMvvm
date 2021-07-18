package com.soushin.tinmvvm.app.network

import rxhttp.wrapper.annotation.DefaultDomain

/**
 * @author created by Soushin
 * @time 2020/1/14 18 24
 * 这里要用class  不能用kotlin  DefaultDomain这个注解不支持kotlin
 */
object Api {

    @DefaultDomain
    const val url = "https://www.wanandroid.com"
    const val TIMEOUT = 30L //超时时间
    //        @Domain(name = "Update")//更换域名
    //        var update = "http://update.9158.com"
}