package com.soushin.tinmvvm.mvvm.adapter.itembinder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.chad.library.adapter.base.binder.QuickViewBindingItemBinder
import com.soushin.tinmvvm.databinding.ItemImageBinding
import com.soushin.tinmvvm.mvvm.repository.entity.ImageEntity

/**
 *
 * @auther SouShin
 * @time 2020/6/28 13:56
 */
class ImageItemBinder : QuickViewBindingItemBinder<ImageEntity,ItemImageBinding>() {

    override fun convert(holder: BinderVBHolder<ItemImageBinding>, data: ImageEntity) {
        holder.viewBinding.ivImage.setImageResource(data.imgRes!!)
    }

    override fun onCreateViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemImageBinding {
        return ItemImageBinding.inflate(layoutInflater,parent,false)
    }


}
