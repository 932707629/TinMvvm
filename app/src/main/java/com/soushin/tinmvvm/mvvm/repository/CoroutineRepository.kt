package com.soushin.tinmvvm.mvvm.repository

import com.soushin.tinmvvm.app.utils.DataUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import me.soushin.tinmvvm.base.BaseRepository
import kotlin.time.Duration

/**
 * ================================================
 * Description:
 * <p>
 * Created by TinMvvmTemplate on 03/06/2020 14:42
 * ================================================
 */

class CoroutineRepository : BaseRepository() {

    val currentNumberFlow: Flow<String> = flow {
        var counter = 0
        while(counter<10) {
            counter++
            delay(2000)
            emit(DataUtils.buildRandom())
        }
    }


}