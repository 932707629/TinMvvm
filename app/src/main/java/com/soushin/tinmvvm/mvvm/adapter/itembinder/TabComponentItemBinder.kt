package com.soushin.tinmvvm.mvvm.adapter.itembinder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.chad.library.adapter.base.binder.QuickViewBindingItemBinder
import com.soushin.tinmvvm.databinding.ItemTabComponentBinding

class TabComponentItemBinder : QuickViewBindingItemBinder<String, ItemTabComponentBinding>() {

    override fun convert(holder: BinderVBHolder<ItemTabComponentBinding>, data: String) {
        holder.viewBinding.tvComponentTitle.text = data
    }

    override fun onCreateViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemTabComponentBinding {
        return ItemTabComponentBinding.inflate(layoutInflater,parent,false)
    }


}