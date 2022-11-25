package com.soushin.tinmvvm.app

import com.soushin.tinmvvm.R
import com.tencent.mmkv.MMKV

/**
 * 应用内数据缓存
 */
class AppData private constructor(){

    companion object {
        private val instance: AppData by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            AppData()
        }
        @JvmStatic
        fun get() : AppData {
            return instance
        }
    }

    private val mmkv by lazy { MMKV.defaultMMKV()!! }




}