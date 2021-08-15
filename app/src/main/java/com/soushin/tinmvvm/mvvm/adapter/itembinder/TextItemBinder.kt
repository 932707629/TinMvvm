package com.soushin.tinmvvm.mvvm.adapter.itembinder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.chad.library.adapter.base.binder.QuickViewBindingItemBinder
import com.soushin.tinmvvm.databinding.ItemTextBinding
import com.soushin.tinmvvm.mvvm.repository.entity.CategoryEntity

/**
 *
 * @auther SouShin
 * @time 2020/6/28 14:55
 */
class TextItemBinder : QuickViewBindingItemBinder<CategoryEntity, ItemTextBinding>() {

    override fun convert(holder: BinderVBHolder<ItemTextBinding>, data: CategoryEntity) {
        holder.viewBinding.text = data
    }

    override fun onCreateViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemTextBinding {
        return ItemTextBinding.inflate(layoutInflater,parent,false)
    }

}