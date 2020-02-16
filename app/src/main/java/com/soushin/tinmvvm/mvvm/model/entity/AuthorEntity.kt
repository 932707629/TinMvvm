package com.soushin.tinmvvm.mvvm.model.entity

import com.soushin.tinmvvm.base.BaseProviderMultiEntity

/**
 * @author created by Soushin
 * @time 2020/1/20 15 46
 */
data class AuthorEntity(
    var itemType: Int?,
    var spanSize:Int?
) : BaseProviderMultiEntity() {

    override fun getItemType(): Int {
        return this.itemType!!
    }


}