package com.soushin.tinmvvm.utils

import com.soushin.tinmvvm.mvvm.model.entity.AuthorEntity

/**
 * @author created by Soushin
 * @time 2020/1/20 16 03
 */
object DataUtils {


    fun getRecyclerDatas():MutableList<AuthorEntity>{
        val datas= mutableListOf<AuthorEntity>()
        for (i in 1..20){
            datas.add(AuthorEntity())
        }
        return datas
    }


}