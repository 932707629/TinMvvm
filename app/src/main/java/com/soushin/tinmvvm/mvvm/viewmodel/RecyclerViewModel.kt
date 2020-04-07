package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import com.caesarlib.brvahbinding.BR
import com.caesarlib.brvahbinding.CSBravhItemBinding
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.base.BaseAdapter
import com.soushin.tinmvvm.base.BaseBindingViewModel
import com.soushin.tinmvvm.base.SingleLiveEvent
import com.soushin.tinmvvm.mvvm.model.RecyclerModel
import com.soushin.tinmvvm.mvvm.model.entity.MultiOneEntity
import com.soushin.tinmvvm.mvvm.model.entity.MultiTwoEntity
import io.reactivex.Single

/**
 * ================================================
 * Description: 配置了Adapter的功能
 * <p>
 * Created by TinMvvmTemplate on 03/02/2020 16:36
 * ================================================
 */

class RecyclerViewModel : BaseBindingViewModel<MultiItemEntity,RecyclerModel> {

    val refresh=SingleLiveEvent<Boolean>()

    constructor(application: Application) : super(application, RecyclerModel())

    override fun initDatas() {
        itemBinding= hashMapOf()
        itemBinding?.let {
            it[BaseAdapter.ITEM_ONE]=CSBravhItemBinding<MultiOneEntity>(BR.data, R.layout.item_recycler)
            it[BaseAdapter.ITEM_TWO]=CSBravhItemBinding<MultiTwoEntity>(BR.data, R.layout.layout_item_text)
            it[BaseAdapter.ITEM_THREE]=CSBravhItemBinding<MultiTwoEntity>(BR.data, R.layout.item_category)
        }
    }


    override fun load() {
        model?.getDatas()?.let {
            load(it)
            refresh.value=true
        }
    }



}
