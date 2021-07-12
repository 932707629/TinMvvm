package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.soushin.tinmvvm.mvvm.adapter.BaseAdapter
import com.soushin.tinmvvm.mvvm.model.MultiplexModel
import com.soushin.tinmvvm.mvvm.model.entity.AuthorEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.soushin.tinmvvm.base.BaseViewModel
import java.util.ArrayList

/**
 * ================================================
 * Description:
 * <p>
 * Created by TinMvvmTemplate on 01/20/2020 15:26
 * ================================================
 */

class MultiplexViewModel(application: Application) :
    BaseViewModel<MultiplexModel>(application, MultiplexModel()) {

    var viewEvent = MutableLiveData<MutableList<AuthorEntity>>()
    private val strArray = arrayOf("关注", "推荐", "视频", "直播", "图片", "段子", "精华", "热门")

    fun loadData(){
        launch {
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
