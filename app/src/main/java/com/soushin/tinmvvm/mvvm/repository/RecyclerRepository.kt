package com.soushin.tinmvvm.mvvm.repository

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.soushin.tinmvvm.mvvm.repository.entity.MultiOneEntity
import com.soushin.tinmvvm.mvvm.repository.entity.MultiThreeEntity
import com.soushin.tinmvvm.mvvm.repository.entity.MultiTwoEntity
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import me.soushin.tinmvvm.base.BaseRepository
import java.util.*

/**
 * ================================================
 * Description:
 * <p>
 * Created by TinMvvmTemplate on 03/02/2020 16:36
 * ================================================
 */

class RecyclerRepository : BaseRepository() {

    fun getData(): Flowable<List<MultiItemEntity>> {
        return Flowable.create({ emitter ->
            val data = ArrayList<MultiItemEntity>()
            data.add(MultiOneEntity("这货是个标题1"))
            data.add(MultiTwoEntity("这货是个标题2"))
            data.add(MultiThreeEntity("这货是个标题3"))
            data.add(MultiTwoEntity("这货是个标题2"))
            data.add(MultiTwoEntity("这货是个标题2"))
            data.add(MultiThreeEntity("这货是个标题3"))
            data.add(MultiTwoEntity("这货是个标题2"))
            data.add(MultiTwoEntity("这货是个标题2"))
            data.add(MultiTwoEntity("这货是个标题2"))
            data.add(MultiOneEntity("这货是个标题1"))
            data.add(MultiThreeEntity("这货是个标题3"))
            data.add(MultiThreeEntity("这货是个标题3"))
            data.add(MultiOneEntity("这货是个标题1"))
            emitter.onNext(data)
            emitter.onComplete()
        }, BackpressureStrategy.BUFFER)
    }

}