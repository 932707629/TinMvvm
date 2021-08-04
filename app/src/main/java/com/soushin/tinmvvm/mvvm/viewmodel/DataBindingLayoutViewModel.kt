package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import android.view.View
import androidx.databinding.*
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.app.throttleClick
import me.soushin.tinmvvm.base.BaseViewModel
import com.soushin.tinmvvm.mvvm.repository.DataBindingLayoutRepository
import me.soushin.tinmvvm.BR
import java.util.*
import kotlin.random.Random

class DataBindingLayoutViewModel(application: Application) :
    BaseViewModel<DataBindingLayoutRepository>(application, DataBindingLayoutRepository()){

    val firstName by lazy { ObservableField<String>() }
    val lastName by lazy { ObservableField<String>() }
    val age = ObservableInt()
    val arrayMap = ObservableArrayMap<String, Any>().apply {
        put("firstName", "Google")
        put("lastName", "Inc.")
        put("age", 17)
    }
    val arrayList = ObservableArrayList<Any>().apply {
        add("Google")
        add("Inc.")
        add(17)
    }

    val user = User()
    val firstList by lazy { listOf("赵","钱","孙","李","周","吴","郑","王") }
    val lastList by lazy { listOf("京","鲁","豫","冀","津","陕","甘","黑","沪","渝","川","新") }
    fun onViewClick() = throttleClick {
        when(it.id){
            R.id.fab_change->{
                user.age= Random.nextInt(1000);
                user.firstName = firstList[Random.nextInt(firstList.size)]
                user.lastName = lastList[Random.nextInt(lastList.size)]

            }
        }
    }



}



class User : BaseObservable() {

    @get:Bindable
    var firstName: String = ""
        set(value) {
            field = value
            println("firstName发出更新=$field")
            notifyPropertyChanged(BR.DataBindingLayoutViewModel)
        }

    @get:Bindable
    var lastName: String = ""
        set(value) {
            field = value
            println("lastName发出更新=$field")
            notifyPropertyChanged(BR.DataBindingLayoutViewModel)
        }

    @get:Bindable
    var age: Int = 0
        set(value) {
            field = value
            println("age发出更新=$field")
            notifyPropertyChanged(BR.DataBindingLayoutViewModel)
        }

}



