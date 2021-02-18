package com.soushin.tinmvvm.network;

import com.soushin.tinmvvm.databinding.ActivityCoroutineBinding;

import rxhttp.wrapper.annotation.DefaultDomain;

/**
 * @author created by Soushin
 * @time 2020/1/14 18 24
 * 这里要用class  不能用kotlin  DefaultDomain这个注解不支持kotlin
 */
public class Api {

    @DefaultDomain
    public static final String url ="https://www.wanandroid.com";

    public static final Long TIMEOUT=30L;//超时时间


//        @Domain(name = "Update")//更换域名
//        var update = "http://update.9158.com"
}
