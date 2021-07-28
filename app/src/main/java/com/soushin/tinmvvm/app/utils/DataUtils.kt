package com.soushin.tinmvvm.app.utils

import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.mvvm.repository.entity.ImageEntity

/**
 * @author created by Soushin
 * @time 2020/1/20 16 03
 */
object DataUtils {


    fun getRecyclerData():MutableList<Any>{
        val data= mutableListOf<Any>()
        for (i in 1..12){
            data.add(ImageEntity(R.mipmap.ic_launcher))
        }
        for (i in 1..20){
            data.add("这里是一个简单的字符串$i")
        }
        return data
    }


}