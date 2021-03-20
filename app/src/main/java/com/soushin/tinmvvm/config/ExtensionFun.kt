package com.soushin.tinmvvm.config

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.hjq.toast.ToastUtils
import me.soushin.tinmvvm.base.BaseFragment

/**
 * 扩展方法
 * @author created by Soushin
 * @time 2020/1/8 11 12
 */

fun AppCompatActivity.showToasty(msg:Any?){
    ToastUtils.show(msg)
}

fun AppCompatActivity.go(clazz:Class<*>){
    startActivity(Intent(getThis(),clazz))
}

fun AppCompatActivity.getThis(): Context {
    return this
}

fun BaseFragment<*, *>.showToasty(msg:Any?){
    ToastUtils.show(msg)
}

fun BaseFragment<*, *>.go(clazz:Class<*>){
    startActivity(Intent(mContext,clazz))
}






