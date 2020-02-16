package com.soushin.tinmvvm.utils

import com.soushin.tinmvvm.base.BaseAdapter
import com.soushin.tinmvvm.mvvm.model.entity.AuthorEntity

/**
 * @author created by Soushin
 * @time 2020/1/20 16 03
 */
object DataUtils {


    fun getRecyclerDatas():MutableList<AuthorEntity>{
        val datas= mutableListOf<AuthorEntity>()
        datas.add(AuthorEntity(BaseAdapter.ITEM_ONE,3, arrayListOf(),""))
        datas.add(AuthorEntity(BaseAdapter.ITEM_ONE,1, arrayListOf(),""))
        datas.add(AuthorEntity(BaseAdapter.ITEM_ONE,1, arrayListOf(),""))
        datas.add(AuthorEntity(BaseAdapter.ITEM_ONE,1, arrayListOf(),""))
        datas.add(AuthorEntity(BaseAdapter.ITEM_ONE,2, arrayListOf(),""))
        datas.add(AuthorEntity(BaseAdapter.ITEM_ONE,1, arrayListOf(),""))
//        for (i in 1..10){
            datas.add(AuthorEntity(BaseAdapter.ITEM_ONE,3, arrayListOf(),""))
//        }
        return datas
    }


}