package com.soushin.tinmvvm.mvvm.repository.entity

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.soushin.tinmvvm.mvvm.adapter.BaseAdapter

/**
 * @author created by Soushin
 * @time 2020/1/14 13 44
 */
data class CategoryEntity (
    var children: List<Any>?=null,
    var courseId: Int?=null, // 13
    var id: Int?=null, // 408
    var name: String?=null, // 鸿洋
    var order: Int?=null, // 190000
    var parentChapterId: Int?=null, // 407
    var userControlSetTop: Boolean?=null, // false
    var visible: Int?=null, // 1
) : MultiItemEntity {

    override val itemType: Int
        get() = BaseAdapter.ITEM_ONE

}