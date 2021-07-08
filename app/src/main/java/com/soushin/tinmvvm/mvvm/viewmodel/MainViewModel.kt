package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.soushin.tinmvvm.mvvm.model.MainModel
import me.soushin.tinmvvm.base.BaseViewModel

/**
 * 视图模型
 * ViewModel 可以用作储存界面数据，取代Bundle savedInstanceState存储数据。
 * ViewModel就是一个存储着我们界面数据的类，界面数据的设定全部来自自定义的ViewModel。
 *
 * @author created by Soushin
 * @time 2020/1/7 13 46
 */
class MainViewModel(application: Application) : BaseViewModel<MainModel>(application, MainModel()) {

}