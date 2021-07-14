package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import me.soushin.tinmvvm.base.BaseViewModel
import com.soushin.tinmvvm.mvvm.model.BottomNavigationModel

class BottomNavigationViewModel(application: Application) :
    BaseViewModel<BottomNavigationModel>(application, BottomNavigationModel()) {

}