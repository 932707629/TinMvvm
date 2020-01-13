package com.soushin.tinmvvm.network

import rxhttp.wrapper.annotation.DefaultDomain
import rxhttp.wrapper.annotation.Domain




/**
 * 项目Api接口
 * @author created by Soushin
 * @time 2020/1/13 16 16
 */
class Api {

    companion object{
        @DefaultDomain
        val url: String ="https://www.wanandroid.com"

        val TIMEOUT=30L//超时时间

//        @Domain(name = "Update")//更换域名
//        var update = "http://update.9158.com"
    }



}