package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import me.soushin.tinmvvm.base.BaseViewModel
import com.soushin.tinmvvm.mvvm.repository.MotionLayoutRepository

class MotionLayoutViewModel(application: Application) :
    BaseViewModel<MotionLayoutRepository>(application, MotionLayoutRepository()) {

}