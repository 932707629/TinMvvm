package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import com.soushin.tinmvvm.mvvm.repository.RecyclerRepository
import me.soushin.tinmvvm.base.BaseViewModel

/**
 *
 * @auther SouShin
 * @time 2020/6/28 10:26
 */
class RecyclerViewModel: BaseViewModel<RecyclerRepository> {

    constructor(application: Application) : super(application, RecyclerRepository()) {

    }

}