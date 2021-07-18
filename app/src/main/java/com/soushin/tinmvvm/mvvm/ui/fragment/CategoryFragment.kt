package com.soushin.tinmvvm.mvvm.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.blankj.ALog
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.databinding.FragmentCategoryBinding
import com.soushin.tinmvvm.mvvm.viewmodel.CategoryViewModel
import com.soushin.tinmvvm.mvvm.viewmodel.DemoViewModel
import me.soushin.tinmvvm.base.DataBindingFragment
import me.soushin.tinmvvm.config.DataBindingConfig

/**
 * @author created by Soushin
 * @time 2020/1/14 15 25
 */
class CategoryFragment : DataBindingFragment<FragmentCategoryBinding, CategoryViewModel>() {

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) {
        ALog.e("开始初始化了")
    }

    companion object {
        fun newInstance(): CategoryFragment {
            return CategoryFragment()
        }
    }

    //配置当前页面的内容 各项参数都可为空
    //BR.xxxxViewModel是kotlin-kapt插件默认生成的 对应xml文件里的xxxxViewModel
    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(layoutId = R.layout.fragment_category,variableId = BR.categoryViewModel,
            vmClass = CategoryViewModel::class.java)
    }

}