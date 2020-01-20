package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import com.soushin.tinmvvm.base.BaseViewModel
import com.soushin.tinmvvm.mvvm.model.RecyclerModel

/**
 * ================================================
 * Description:
 * <p>
 * Created by TinMvvmTemplate on 01/20/2020 15:26
 * ================================================
 */

class RecyclerViewModel : BaseViewModel<RecyclerModel> {

    constructor(application: Application) : super(application, RecyclerModel()) {

    }


}
