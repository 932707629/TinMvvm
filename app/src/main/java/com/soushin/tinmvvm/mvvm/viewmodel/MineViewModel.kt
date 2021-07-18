package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import me.soushin.tinmvvm.base.BaseViewModel
import com.soushin.tinmvvm.mvvm.repository.MineRepository

class MineViewModel(application: Application) :
    BaseViewModel<MineRepository>(application, MineRepository()) {

}