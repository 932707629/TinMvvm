package com.soushin.tinmvvm.mvvm.model.entity

import com.chad.library.adapter.base.entity.MultiItemEntity
import me.soushin.tinmvvm.base.BaseAdapter

/**
 *
 * @auther SouShin
 * @time 2020/3/3 11:21
 */
data class MultiTwoEntity(var m:String):MultiItemEntity {
    override val itemType: Int
        get() = BaseAdapter.ITEM_TWO

}