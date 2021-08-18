package com.soushin.tinmvvm.mvvm.ui.fragment

import android.os.Bundle
import android.view.View
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.databinding.FragmentDataBindingLayoutBinding
import com.soushin.tinmvvm.mvvm.viewmodel.DataBindingLayoutViewModel
import me.soushin.tinmvvm.base.DataBindingFragment
import me.soushin.tinmvvm.config.DataBindingConfig

/**
 * 演示databinding的功能
 *
 * @auther SouShin
 * @time 2021/7/30 15:23
 */
class DataBindingLayoutFragment :
    DataBindingFragment<FragmentDataBindingLayoutBinding, DataBindingLayoutViewModel>() {

    companion object {
        fun newInstance(): DataBindingLayoutFragment {
            return DataBindingLayoutFragment()
        }
    }

    //配置当前页面的内容 各项参数都可为空
    //BR.xxxxViewModel是kotlin-kapt插件默认生成的 对应xml文件里的xxxxViewModel
    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(
            layoutId = R.layout.fragment_data_binding_layout,
            variableId = BR.DataBindingLayoutViewModel,
            vmClass = DataBindingLayoutViewModel::class.java
        )
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {

    }

}




