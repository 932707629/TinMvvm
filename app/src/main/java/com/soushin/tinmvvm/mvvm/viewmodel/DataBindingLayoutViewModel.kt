package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.blankj.ALog
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.app.showToasty
import com.soushin.tinmvvm.app.throttleClick
import com.soushin.tinmvvm.app.utils.toJson
import com.soushin.tinmvvm.mvvm.repository.DataBindingLayoutRepository
import me.soushin.tinmvvm.base.BaseViewModel
import kotlin.random.Random

class DataBindingLayoutViewModel(application: Application) :
    BaseViewModel<DataBindingLayoutRepository>(application, DataBindingLayoutRepository()){

    val userForm = MutableLiveData(User("赵","京",0))
//    val firstName = ObservableField<String>("赵")
//    val lastName = ObservableField<String>("京")
    val firstList by lazy { listOf("赵","钱","孙","李","周","吴","郑","王") }
    val lastList by lazy { listOf("京","鲁","豫","冀","津","陕","甘","黑","沪","渝","川","新") }


    fun onViewClick() = throttleClick {
        when(it.id){
            R.id.btn_change->{
                userForm.value = User(firstList[Random.nextInt(firstList.size)],
                    lastList[Random.nextInt(lastList.size)],Random.nextInt(1000))
//                firstName.set(user.value?.firstName)
//                lastName.set(user.value?.lastName)
            }
        }
    }

}

data class User(
    var firstName: String = "",
    var lastName: String = "",
    var age: Int = 0,
    var imageUrl : String ="https://img0.baidu.com/it/u=2111439368,820623363&fm=253&fmt=auto&app=120&f=PNG?w=900&h=586",
    var error:Int = R.mipmap.ic_launcher
)






