package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import android.view.View
import com.soushin.tinmvvm.mvvm.model.CoroutineModel
import me.soushin.base_lib.base.BaseViewModel

/**
 * ================================================
 * Description:
 * <p>
 * Created by TinMvvmTemplate on 03/06/2020 14:42
 * ================================================
 */

class CoroutineViewModel : BaseViewModel<CoroutineModel> {


    constructor(application: Application) : super(application, CoroutineModel()) {

    }

    fun onClick(): View.OnClickListener {
        return View.OnClickListener {
            model?.start()
        }

    }


}
