package com.soushin.tinmvvm.base

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.text.method.ArrowKeyMovementMethod
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import com.hjq.toast.ToastUtils
import io.reactivex.disposables.Disposable

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

fun BaseFragment<*,*>.showToasty(msg:Any?){
    ToastUtils.show(msg)
}

fun BaseFragment<*,*>.go(clazz:Class<*>){
    startActivity(Intent(mContext,clazz))
}

fun FragmentActivity.useFragment():Boolean{
    return false
}





