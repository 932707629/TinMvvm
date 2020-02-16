package com.soushin.tinmvvm.mvvm.adapter

import com.soushin.tinmvvm.base.BaseAdapter
import com.soushin.tinmvvm.mvvm.model.RecyclerItemProvider
import com.soushin.tinmvvm.mvvm.model.entity.AuthorEntity

/**
 * @author created by Soushin
 * @time 2020/1/20 15 43
 */
class RecyclerAdapter : BaseAdapter<AuthorEntity>() {

    init {
        addItemProvider(RecyclerItemProvider())
    }



}