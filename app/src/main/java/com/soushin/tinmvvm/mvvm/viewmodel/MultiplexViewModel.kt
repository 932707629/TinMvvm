package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import com.soushin.tinmvvm.mvvm.model.MultiplexModel
import me.soushin.tinmvvm.base.BaseViewModel

/**
 * ================================================
 * Description:
 * <p>
 * Created by TinMvvmTemplate on 01/20/2020 15:26
 * ================================================
 */

class MultiplexViewModel :
    BaseViewModel<MultiplexModel> {

    constructor(application: Application) : super(application, MultiplexModel()) {

    }





}
