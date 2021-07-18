package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import me.soushin.tinmvvm.base.BaseViewModel
import com.soushin.tinmvvm.mvvm.repository.ComponentRepository

class ComponentViewModel(application: Application) :
    BaseViewModel<ComponentRepository>(application, ComponentRepository()) {

}