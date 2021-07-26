package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import com.soushin.tinmvvm.mvvm.repository.MultiplexRepository
import me.soushin.tinmvvm.base.BaseViewModel

/**
 * ================================================
 * Description:
 * <p>
 * Created by TinMvvmTemplate on 01/20/2020 15:26
 * ================================================
 */

class MultiplexViewModel(application: Application) :
    BaseViewModel<MultiplexRepository>(application, MultiplexRepository()) {


}
