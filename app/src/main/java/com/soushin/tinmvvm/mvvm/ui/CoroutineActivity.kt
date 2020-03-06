package com.soushin.tinmvvm.mvvm.ui

import android.os.Bundle
import com.blankj.ALog

import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.base.BaseActivity
import com.soushin.tinmvvm.databinding.ActivityCoroutineBinding
import com.soushin.tinmvvm.mvvm.viewmodel.CoroutineViewModel
import kotlinx.coroutines.*

/**
 * ================================================
 * Description:协程演示代码
 * <p>
 * Created by TinMvvmTemplate on 03/06/2020 14:42
 * ================================================
 */

class CoroutineActivity : BaseActivity<ActivityCoroutineBinding, CoroutineViewModel>() {


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_coroutine //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {

    }


    override fun initVariableId(): Int {
        return BR.CoroutineViewModel;//这里是为了绑定ViewModel用的 如果不需要请返回0
    }


}
