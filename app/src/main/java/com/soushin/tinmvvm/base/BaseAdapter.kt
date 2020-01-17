package com.soushin.tinmvvm.base

import com.chad.library.adapter.base.BaseProviderMultiAdapter

/**
 * 基于brvah封装的适配多布局的adapter
 * @author created by Soushin
 * @time 2020/1/14 11 10
 */
open class BaseAdapter<T : BaseProviderMultiEntity> :BaseProviderMultiAdapter<T> {

    companion object{
        //这里的item可以无限添加
        val ITEM_ONE=1
        val ITEM_TWO=2
        val ITEM_THREE=3
        val ITEM_FOUR=4
        val ITEM_FIVE=5
    }

    constructor():super(null)

    override fun getItemType(data: List<T>, position: Int): Int {
        return  data[position].getItemType()
    }

}