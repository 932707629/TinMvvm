package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.soushin.tinmvvm.mvvm.adapter.BaseAdapter
import com.soushin.tinmvvm.mvvm.repository.MultiplexRepository
import com.soushin.tinmvvm.mvvm.repository.entity.AuthorEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    var viewEvent = MutableLiveData<MutableList<AuthorEntity>>()
    private val strArray = arrayOf("关注", "推荐", "视频", "直播", "图片", "段子", "精华", "热门")

    fun loadData(){
        getLifecycleScope().launch {
            withContext(Dispatchers.IO){
                val mDataList = mutableListOf<AuthorEntity>()
                for (i in 0..8) {
                    mDataList.add(AuthorEntity(BaseAdapter.ITEM_ONE, 2, arrayListOf(), ""))
                }
                val childDatas = arrayListOf<String>()
                strArray.forEach {
                    childDatas.add(it)
                }
                mDataList.add(AuthorEntity(BaseAdapter.ITEM_TWO, 2, childDatas, ""))
                viewEvent.postValue(mDataList)
            }
        }
    }

}
