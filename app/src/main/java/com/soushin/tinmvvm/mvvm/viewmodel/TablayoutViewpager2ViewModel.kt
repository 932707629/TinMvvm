package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import com.soushin.tinmvvm.mvvm.repository.TabLayoutViewpager2Repository
import me.soushin.tinmvvm.base.BaseViewModel

class TabLayoutViewpager2ViewModel(application: Application) :
    BaseViewModel<TabLayoutViewpager2Repository>(application, TabLayoutViewpager2Repository()) {

}