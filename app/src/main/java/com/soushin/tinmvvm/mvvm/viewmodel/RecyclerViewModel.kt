package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import com.baiyyy.communitypad.app.widget.SingleLiveEvent
import com.soushin.tinmvvm.app.utils.DataUtils
import com.soushin.tinmvvm.mvvm.repository.RecyclerRepository
import com.soushin.tinmvvm.mvvm.repository.entity.ViewTaskEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
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

    //这里的事件做到了每次页面返回的时候调一次 进页面调一次
    var viewEvent = SingleLiveEvent<ViewTaskEvent>()

    fun loadData() {
        getScope().launch {
            withContext(Dispatchers.IO) {
                viewEvent.postValue(ViewTaskEvent(key = 0, value = DataUtils.getRecyclerData()))
            }
        }
    }


}