package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.soushin.tinmvvm.app.utils.DataUtils
import com.soushin.tinmvvm.mvvm.repository.RecyclerRepository
import com.soushin.tinmvvm.mvvm.repository.entity.ViewTaskEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.soushin.tinmvvm.base.BaseViewModel

/**
 *
 * @auther SouShin
 * @time 2020/6/28 10:26
 */
class RecyclerViewModel(application: Application) :
    BaseViewModel<RecyclerRepository>(application, RecyclerRepository()) {

    var viewEvent = MutableLiveData<ViewTaskEvent>()

    fun loadData() {
        getCoroutineScope().launch {
            withContext(Dispatchers.IO) {
                viewEvent.postValue(ViewTaskEvent(key = 0, value = DataUtils.getRecyclerData()))
            }
        }
    }


}