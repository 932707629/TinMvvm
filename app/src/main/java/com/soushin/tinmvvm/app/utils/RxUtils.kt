package com.soushin.tinmvvm.app.utils

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.FlowableTransformer
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.core.SingleTransformer
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

    fun <T> applySingleSchedulers() : SingleTransformer<T, T> {
        return SingleTransformer<T,T>{
            return@SingleTransformer it.subscribeOn(Schedulers.io())
                .doOnSubscribe{
                    //显示loading
                }.subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    //隐藏loading
                }
        }
    }

    fun <T> applySingleAsync() : SingleTransformer<T,T> {
        return SingleTransformer<T,T>{
            return@SingleTransformer it.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun <T> applyFlowableSchedulers() : FlowableTransformer<T, T> {
        return FlowableTransformer<T,T>{
            return@FlowableTransformer it.subscribeOn(Schedulers.io())
                .doOnSubscribe{
                    //显示loading
                }.subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    //隐藏loading
                }
        }
    }

    fun <T> applyFlowableAsync() : FlowableTransformer<T,T> {
        return FlowableTransformer<T,T>{
            return@FlowableTransformer it.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }


}