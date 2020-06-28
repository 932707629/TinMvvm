package com.soushin.tinmvvm.mvvm.adapter.itembinder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.chad.library.adapter.base.binder.QuickViewBindingItemBinder
import com.soushin.tinmvvm.databinding.ItemTextBinding

/**
 *
 * @auther SouShin
 * @time 2020/6/28 14:55
 */
class TextItemBinder : QuickViewBindingItemBinder<String, ItemTextBinding>() {

    override fun convert(holder: BinderVBHolder<ItemTextBinding>, data: String) {
        holder.viewBinding.tvText.text=data
    }

    override fun onCreateViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemTextBinding {
        return ItemTextBinding.inflate(layoutInflater,parent,false)
    }

}