package com.soushin.tinmvvm.app.utils

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 *
 * @auther SouShin
 * @time 2020/10/8 14:16
 */
object RxUtils {

    fun <T> applySchedulers() : ObservableTransformer<T, T> {
        return ObservableTransformer<T,T>{
            return@ObservableTransformer it.subscribeOn(Schedulers.io())
                .doOnSubscribe{
                    //显示loading
                }.subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    //隐藏loading
                }
        }
    }

    fun <T> applyAsync() : ObservableTransformer<T,T> {
        return ObservableTransformer<T,T>{
            return@ObservableTransformer it.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }


}