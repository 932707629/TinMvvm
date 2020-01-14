package com.soushin.tinmvvm.mvvm.adapter

import com.soushin.tinmvvm.base.BaseAdapter
import com.soushin.tinmvvm.mvvm.model.entity.CategoryEntity

/**
 * @author created by Soushin
 * @time 2020/1/14 13 43
 */
class CategoryAdapter : BaseAdapter<CategoryEntity>() {

    init {
        addItemProvider(CategoryProvider())
    }


}