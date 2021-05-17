package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.alibaba.fastjson.JSONObject
import com.blankj.ALog
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.mvvm.model.CategoryModel
import com.soushin.tinmvvm.mvvm.model.entity.CategoryEntity
import com.soushin.tinmvvm.utils.RxUtils
import me.soushin.tinmvvm.base.BaseViewModel
import me.soushin.tinmvvm.config.HttpHandleCallBack
import me.soushin.tinmvvm.utils.throttleClick


/**
 * @author created by Soushin
 * @time 2020/1/14 18 05
 */
class CategoryViewModel(application: Application) :
    BaseViewModel<CategoryModel>(application, CategoryModel()) {

    var btnContent= MutableLiveData<String>()
    var pageSkip=MutableLiveData<Int>()

    fun onClickBtnContent() = throttleClick {
        when(it.id){
            R.id.btn_content->{
                requestData()
            }
            R.id.btn_next_page->{
                pageSkip.postValue(1)
            }
            R.id.btn_last_page->{
                pageSkip.postValue(2)
            }
        }
    }

    private fun requestData() {
        model.requestDatas()
            .compose(RxUtils.applySchedulers())
            .subscribe(object : HttpHandleCallBack<List<CategoryEntity>>(lifecycle!!){
                override fun onNext(result: List<CategoryEntity>) {
                    super.onNext(result)
                    ALog.e("服务器返回结果", JSONObject.toJSONString(result))
                    btnContent.value=JSONObject.toJSONString(result)
                }
            })
    }


}