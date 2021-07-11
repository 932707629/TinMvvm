package com.soushin.tinmvvm.mvvm.ui.fragment

import android.os.Bundle
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.databinding.FragmentThreadPoolBinding
import com.soushin.tinmvvm.mvvm.viewmodel.ThreadPoolViewModel
import me.soushin.tinmvvm.base.BaseActivity
import me.soushin.tinmvvm.base.BaseFragment

/**
 * ================================================
 * Description:
 * <p>
 * Created by TinMvvmTemplate on 06/24/2020 11:28
 * ================================================
 */

class ThreadPoolFragment : BaseFragment<FragmentThreadPoolBinding, ThreadPoolViewModel>() {

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initVariableId(): Int {
        return BR.ThreadPoolViewModel;//这里是为了绑定ViewModel用的 如果不需要请返回0
    }


}
