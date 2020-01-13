package com.soushin.tinmvvm.mvvm.model

import android.util.AndroidException
import com.soushin.tinmvvm.base.BaseModel
import com.soushin.tinmvvm.network.Api
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import rxhttp.RxHttpPlugins
import rxhttp.wrapper.param.RxHttp
import java.io.File

/**
 *
 * @author created by Soushin
 * @time 2020/1/8 09 11
 */
class MainModel :BaseModel(){


    fun getDatas():String{

        return "1231231312"
    }

    fun requestDatas():Any{

        return RxHttp.get("wxarticle/chapters/json")
            .asObject(MainModel::class.java)
            .subscribe()
    }

}