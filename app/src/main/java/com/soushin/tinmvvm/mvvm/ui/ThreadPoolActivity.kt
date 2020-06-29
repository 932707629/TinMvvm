package com.soushin.tinmvvm.mvvm.ui

import android.os.Bundle

import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.databinding.ActivityThreadPoolBinding
import com.soushin.tinmvvm.mvvm.viewmodel.ThreadPoolViewModel
import me.soushin.tinmvvm.base.BaseActivity

/**
 * ================================================
 * Description:
 * <p>
 * Created by TinMvvmTemplate on 06/24/2020 11:28
 * ================================================
 */

class ThreadPoolActivity : BaseActivity<ActivityThreadPoolBinding, ThreadPoolViewModel>() {


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_thread_pool //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun initVariableId(): Int {
        return BR.ThreadPoolViewModel;//这里是为了绑定ViewModel用的 如果不需要请返回0
    }


}
