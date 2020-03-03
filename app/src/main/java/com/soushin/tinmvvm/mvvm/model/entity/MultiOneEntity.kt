package com.soushin.tinmvvm.mvvm.model.entity

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.soushin.tinmvvm.base.BaseAdapter

/**
 *
 * @auther SouShin
 * @time 2020/3/3 11:21
 */
data class MultiOneEntity(var m:String):MultiItemEntity {
    override fun getItemType(): Int {
        return BaseAdapter.ITEM_ONE
    }
}