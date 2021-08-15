package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import android.view.View
import androidx.databinding.*
import androidx.lifecycle.MutableLiveData
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.app.throttleClick
import me.soushin.tinmvvm.base.BaseViewModel
import com.soushin.tinmvvm.mvvm.repository.DataBindingLayoutRepository
import me.soushin.tinmvvm.BR
import java.util.*
import kotlin.random.Random

class DataBindingLayoutViewModel(application: Application) :
    BaseViewModel<DataBindingLayoutRepository>(application, DataBindingLayoutRepository()){

    val user = MutableLiveData(User("赵","京",0))
    val firstName = ObservableField<String>("赵")
    val lastName = ObservableField<String>("京")
    val firstList by lazy { listOf("赵","钱","孙","李","周","吴","郑","王") }
    val lastList by lazy { listOf("京","鲁","豫","冀","津","陕","甘","黑","沪","渝","川","新") }


    fun onViewClick() = throttleClick {
        when(it.id){
            R.id.btn_change->{
                user.postValue(User(firstList[Random.nextInt(firstList.size)],
                    lastList[Random.nextInt(lastList.size)],Random.nextInt(1000)))
                firstName.set(user.value?.firstName)
                lastName.set(user.value?.lastName)
            }
        }
    }

}

data class User(
    var firstName: String = "",
    var lastName: String = "",
    var age: Int = 0,
    var imageUrl : String ="https://wx1.sinaimg.cn/mw690/002Po4pSly1gswgs812stj60u01hcndz02.jpg",
    var error:Int = R.mipmap.ic_launcher
)






