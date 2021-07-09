package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import me.soushin.tinmvvm.base.BaseViewModel
import com.soushin.tinmvvm.mvvm.model.HomeModel

class HomeViewModel(application: Application) :
    BaseViewModel<HomeModel>(application, HomeModel()) {
    var tvContent= MutableLiveData<String>("Hello World")

    fun getDatas(){
        tvContent.value= model.getDatas()
    }
}