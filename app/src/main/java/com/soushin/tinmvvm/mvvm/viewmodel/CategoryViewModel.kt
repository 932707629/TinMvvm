package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import com.alibaba.fastjson.JSONObject
import com.blankj.ALog
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.app.utils.RxUtils
import com.soushin.tinmvvm.mvvm.repository.CategoryRepository
import com.soushin.tinmvvm.mvvm.repository.entity.CategoryEntity
import me.soushin.tinmvvm.base.BaseViewModel
import me.soushin.tinmvvm.rxerror.handler.ErrorHandleSubscriber
import me.soushin.tinmvvm.utils.throttleClick


/**
 * @author created by Soushin
 * @time 2020/1/14 18 05
 */
class CategoryViewModel(application: Application) :
    BaseViewModel<CategoryRepository>(application, CategoryRepository()) {

    var btnContent= MutableLiveData<String>()

    fun onClickBtnContent() = throttleClick {
        when(it.id){
            R.id.btn_content->{
                requestData()
            }
            R.id.btn_next_page->{
                ///自己跳转自己 多次重复打开一个页面
                //.setLaunchSingleTop(true)会让堆栈中始终保持fragment一个实例
                Navigation.findNavController(it).navigate(R.id.action_categoryFragment_self)
            }
            R.id.btn_last_page->{
                Navigation.findNavController(it).popBackStack();
            }
        }
    }

    private fun requestData() {
        mRepository.requestData()
            .compose(RxUtils.applySchedulers())
            .subscribe(object : ErrorHandleSubscriber<MutableList<CategoryEntity>>(getLifecycleOwner(),getErrorHandler()){
                override fun onNext(result: MutableList<CategoryEntity>) {
                    super.onNext(result)
                    ALog.e("服务器返回结果", JSONObject.toJSONString(result))
                    btnContent.value=JSONObject.toJSONString(result)
                }
            })
    }


}