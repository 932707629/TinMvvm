package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import com.soushin.tinmvvm.mvvm.model.RecyclerModel
import me.soushin.base_lib.base.BaseViewModel

/**
 *
 * @auther SouShin
 * @time 2020/6/28 10:26
 */
class RecyclerViewModel: BaseViewModel<RecyclerModel> {

    constructor(application: Application) : super(application, RecyclerModel()) {

    }

}