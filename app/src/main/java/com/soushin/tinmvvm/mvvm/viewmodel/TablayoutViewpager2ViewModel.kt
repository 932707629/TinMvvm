package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import me.soushin.tinmvvm.base.BaseViewModel
import com.soushin.tinmvvm.mvvm.repository.TabLayoutViewpager2Repository

class TabLayoutViewpager2ViewModel(application: Application) :
    BaseViewModel<TabLayoutViewpager2Repository>(application, TabLayoutViewpager2Repository()) {

}