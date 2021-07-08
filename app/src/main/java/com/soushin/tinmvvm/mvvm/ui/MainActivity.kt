package com.soushin.tinmvvm.mvvm.ui

import android.os.Bundle
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.databinding.ActivityMainBinding
import com.soushin.tinmvvm.mvvm.viewmodel.MainViewModel
import me.soushin.tinmvvm.base.BaseActivity


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun initView(savedInstanceState: Bundle?) {

    }

    //BR.xxxxViewModel是kotlin-kapt插件默认生成的 对应xml文件里的xxxxViewModel
    override fun initVariableId(): Int {
        return BR.mainViewModel
    }



}
