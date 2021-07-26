package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import com.soushin.tinmvvm.mvvm.repository.SplashRepository
import me.soushin.tinmvvm.base.BaseViewModel

class SplashViewModel(application: Application) :
    BaseViewModel<SplashRepository>(application, SplashRepository()) {

}