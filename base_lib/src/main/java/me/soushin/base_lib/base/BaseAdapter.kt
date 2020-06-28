package me.soushin.base_lib.base

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * 基于brvah封装的适配多布局的adapter
 * @author created by Soushin
 * @time 2020/1/14 11 10
 */
open class BaseAdapter<T : MultiItemEntity,K: BaseViewHolder> :BaseMultiItemQuickAdapter<T,K> {

    override fun convert(helper: K, item: T) {

    }

    companion object{
        //这里的item可以无限添加
        val ITEM_ONE=1
        val ITEM_TWO=2
        val ITEM_THREE=3
        val ITEM_FOUR=4
        val ITEM_FIVE=5
        val ITEM_SIX=6
        val ITEM_SEVEN=7
        val ITEM_EIGHT=8
        val ITEM_NINE=9

    }

    constructor():super(null)

}