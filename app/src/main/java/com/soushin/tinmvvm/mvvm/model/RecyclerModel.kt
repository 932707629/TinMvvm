package com.soushin.tinmvvm.mvvm.model

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.soushin.tinmvvm.mvvm.model.entity.MultiOneEntity
import com.soushin.tinmvvm.mvvm.model.entity.MultiThreeEntity
import com.soushin.tinmvvm.mvvm.model.entity.MultiTwoEntity
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import me.soushin.tinmvvm.base.BaseModel
import java.util.*

/**
 * ================================================
 * Description:
 * <p>
 * Created by TinMvvmTemplate on 03/02/2020 16:36
 * ================================================
 */

class RecyclerModel : BaseModel() {

    fun getDatas(): Flowable<List<MultiItemEntity>>{
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