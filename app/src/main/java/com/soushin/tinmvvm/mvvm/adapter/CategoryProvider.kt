package com.soushin.tinmvvm.mvvm.adapter

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.base.BaseAdapter
import com.soushin.tinmvvm.mvvm.model.entity.CategoryEntity

/**
 * @author created by Soushin
 * @time 2020/1/14 13 48
 */
class CategoryProvider : BaseItemProvider<CategoryEntity>() {


    override val itemViewType: Int
        get() = BaseAdapter.ITEM_ONE

    override val layoutId: Int
        get() = R.layout.item_category1

    override fun onViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<ViewDataBinding>(viewHolder.itemView)
    }

    override fun convert(helper: BaseViewHolder, data: CategoryEntity?) {
        helper.setText(R.id.tv_chapter_name,data?.name)
    }



}