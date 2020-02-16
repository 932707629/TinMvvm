package com.soushin.tinmvvm.mvvm.model.provider

import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.base.BaseAdapter
import com.soushin.tinmvvm.mvvm.model.entity.AuthorEntity

/**
 * @author created by Soushin
 * @time 2020/1/20 15 53
 */
class RecyclerTextProvider : BaseItemProvider<AuthorEntity>() {
    override val itemViewType: Int
        get() = BaseAdapter.ITEM_ONE
    override val layoutId: Int
        get() = R.layout.layout_item_text

    override fun convert(helper: BaseViewHolder, data: AuthorEntity?) {
        helper.setText(R.id.textView,data?.getItemType().toString()+"0")
    }


}