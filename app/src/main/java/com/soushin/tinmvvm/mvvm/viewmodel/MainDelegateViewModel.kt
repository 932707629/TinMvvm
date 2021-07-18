package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import me.soushin.tinmvvm.base.BaseViewModel
import com.soushin.tinmvvm.mvvm.repository.MainDelegateRepository

class MainDelegateViewModel(application: Application) :
    BaseViewModel<MainDelegateRepository>(application, MainDelegateRepository()) {

}