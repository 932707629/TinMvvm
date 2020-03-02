package com.soushin.tinmvvm.base

import android.app.Application

/**
 *
 * @auther SouShin
 * @time 2020/3/2 17:31
 */
class BaseBindingViewModel<B,M:BaseModel>(application: Application, model: M) :
    BaseViewModel<M>(application, model) {



}