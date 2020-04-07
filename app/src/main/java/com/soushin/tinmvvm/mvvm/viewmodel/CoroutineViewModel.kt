package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import android.view.View
import com.blankj.ALog
import com.soushin.tinmvvm.base.BaseViewModel
import com.soushin.tinmvvm.mvvm.model.CoroutineModel
import kotlinx.coroutines.*

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
