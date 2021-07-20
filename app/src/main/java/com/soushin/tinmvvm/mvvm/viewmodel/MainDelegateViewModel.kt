package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import com.soushin.tinmvvm.mvvm.repository.MainDelegateRepository
import me.soushin.tinmvvm.base.BaseViewModel

class MainDelegateViewModel(application: Application) :
    BaseViewModel<MainDelegateRepository>(application, MainDelegateRepository()) {

}