package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import android.view.View
import com.jeremyliao.liveeventbus.LiveEventBus
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.app.AppData
import com.soushin.tinmvvm.app.GlobalConstants
import com.soushin.tinmvvm.app.throttleClick
import com.soushin.tinmvvm.mvvm.repository.MineRepository
import com.soushin.tinmvvm.mvvm.repository.entity.ViewTaskEvent
import com.soushin.tinmvvm.mvvm.ui.SplashActivity
import com.soushin.tinmvvm.mvvm.ui.fragment.WorkerFragment
import me.soushin.tinmvvm.base.BaseViewModel
import me.soushin.tinmvvm.utils.AppManager

class MineViewModel(application: Application) :
    BaseViewModel<MineRepository>(application, MineRepository()) {

    fun onViewClick():View.OnClickListener{
        return throttleClick {
            AppManager.get().go(SplashActivity::class.java)
        }
    }






}