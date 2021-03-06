package com.soushin.tinmvvm.mvvm.model.entity

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.soushin.tinmvvm.mvvm.adapter.BaseAdapter

/**
 * @author created by Soushin
 * @time 2020/1/14 13 44
 */
data class CategoryEntity (
    var children: List<Any?>?,
    var courseId: Int?, // 13
    var id: Int?, // 408
    var name: String?, // 鸿洋
    var order: Int?, // 190000
    var parentChapterId: Int?, // 407
    var userControlSetTop: Boolean?, // false
    var visible: Int? // 1
) : MultiItemEntity {

    override val itemType: Int
        get() = BaseAdapter.ITEM_ONE

}