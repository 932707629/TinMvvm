package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import me.soushin.tinmvvm.base.BaseViewModel
import com.soushin.tinmvvm.mvvm.repository.SplashRepository

class SplashViewModel(application: Application) :
    BaseViewModel<SplashRepository>(application, SplashRepository()) {

}