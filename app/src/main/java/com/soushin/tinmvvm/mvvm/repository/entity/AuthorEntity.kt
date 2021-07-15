package com.soushin.tinmvvm.mvvm.repository.entity

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * @author created by Soushin
 * @time 2020/1/20 15 46
 */
data class AuthorEntity(
    override val itemType: Int,
    var spanSize:Int?,
    var tabTitleList : ArrayList<String>?,
    var title:String
) : MultiItemEntity {


}