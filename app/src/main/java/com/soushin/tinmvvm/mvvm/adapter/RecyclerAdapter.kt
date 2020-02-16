package com.soushin.tinmvvm.mvvm.adapter

import com.soushin.tinmvvm.base.BaseAdapter
import com.soushin.tinmvvm.mvvm.model.provider.RecyclerTextProvider
import com.soushin.tinmvvm.mvvm.model.entity.AuthorEntity
import com.soushin.tinmvvm.mvvm.model.provider.RecyclerCategoryProvider
import com.soushin.tinmvvm.widget.ChildRecyclerView

/**
 * @author created by Soushin
 * @time 2020/1/20 15 43
 */
class RecyclerAdapter : BaseAdapter<AuthorEntity>() {

    private var recyclerCategoryProvider:RecyclerCategoryProvider?=null

    init {
        addItemProvider(RecyclerTextProvider())
        recyclerCategoryProvider=RecyclerCategoryProvider()
        addItemProvider(recyclerCategoryProvider!!)

    }

    fun getCurrentChildRecyclerView(): ChildRecyclerView? {
        recyclerCategoryProvider?.apply {
            return this.getCurrentChildRecyclerView()
        }
        return null
    }


}