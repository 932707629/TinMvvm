package me.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import me.soushin.tinmvvm.base.BaseViewModel
import me.soushin.tinmvvm.mvvm.model.TinErrorModel

/**
 * ================================================
 * Description:
 * <p>
 * Created by TinMvvmTemplate on 07/24/2020 16:35
 * ================================================
 */

class TinErrorViewModel : BaseViewModel<TinErrorModel> {

    constructor(application: Application) : super(application, TinErrorModel()) {

    }


}
