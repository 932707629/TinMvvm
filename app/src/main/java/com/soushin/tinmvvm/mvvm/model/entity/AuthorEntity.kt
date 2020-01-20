package com.soushin.tinmvvm.mvvm.model.entity

import com.soushin.tinmvvm.base.BaseAdapter
import com.soushin.tinmvvm.base.BaseProviderMultiEntity

/**
 * @author created by Soushin
 * @time 2020/1/20 15 46
 */
class AuthorEntity : BaseProviderMultiEntity() {
    override fun getItemType(): Int {
        return BaseAdapter.ITEM_ONE
    }




}