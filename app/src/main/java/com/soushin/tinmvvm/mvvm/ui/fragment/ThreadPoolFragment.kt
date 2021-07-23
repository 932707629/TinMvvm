package com.soushin.tinmvvm.mvvm.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.databinding.FragmentThreadPoolBinding
import com.soushin.tinmvvm.mvvm.viewmodel.ThreadPoolViewModel
import me.soushin.tinmvvm.base.DataBindingFragment
import me.soushin.tinmvvm.config.DataBindingConfig

/**
 * ================================================
 * Description:
 * <p>
 * Created by TinMvvmTemplate on 06/24/2020 11:28
 * ================================================
 */

class ThreadPoolFragment : DataBindingFragment<FragmentThreadPoolBinding, ThreadPoolViewModel>() {

    override fun initView(view: View, savedInstanceState: Bundle?) {

    }

    //配置当前页面的内容 各项参数都可为空
    //BR.xxxxViewModel是kotlin-kapt插件默认生成的 对应xml文件里的xxxxViewModel
    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(layoutId = R.layout.fragment_thread_pool,variableId = BR.ThreadPoolViewModel,
            vmClass = ThreadPoolViewModel::class.java)
    }

    companion object {
        fun newInstance(): ThreadPoolFragment {
            return ThreadPoolFragment()
        }
    }

}
