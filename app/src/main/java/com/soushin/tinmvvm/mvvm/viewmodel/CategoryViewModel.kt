package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.blankj.ALog
import com.soushin.tinmvvm.mvvm.model.CategoryModel
import com.soushin.tinmvvm.mvvm.model.entity.CategoryEntity
import me.soushin.tinmvvm.config.HttpHandleCallBack
import me.soushin.tinmvvm.base.BaseViewModel
import rxhttp.wrapper.utils.GsonUtil

/**
 * @author created by Soushin
 * @time 2020/1/14 18 05
 */
class CategoryViewModel: BaseViewModel<CategoryModel> {

    var btnContent= MutableLiveData<String>()

    constructor(application: Application):super(application, CategoryModel()){

    }

    fun onClickBtnContent()=View.OnClickListener{
        requestData()
    }

    fun requestData() {
        model?.requestDatas()
            ?.subscribe(object : HttpHandleCallBack<List<CategoryEntity>>(){
                override fun onNext(result: List<CategoryEntity>) {
                    super.onNext(result)
                    ALog.e("服务器返回结果",GsonUtil.toJson(result))
                    btnContent.value=GsonUtil.toJson(result)
                }
            })
    }


}