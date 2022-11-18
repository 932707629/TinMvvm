package com.soushin.tinmvvm.mvvm.ui.fragment

import android.os.Bundle
import android.view.View
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.databinding.FragmentCategoryBinding
import com.soushin.tinmvvm.mvvm.adapter.PagingSimpleAdapter
import com.soushin.tinmvvm.mvvm.viewmodel.CategoryViewModel
import me.soushin.tinmvvm.base.DataBindingFragment
import me.soushin.tinmvvm.config.DataBindingConfig

/**
 * @author created by Soushin
 * @time 2020/1/14 15 25
 */
class CategoryFragment : DataBindingFragment<FragmentCategoryBinding, CategoryViewModel>() {

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

    override fun onDestroyView() {
        //不置空会出现adapter内存泄露 详情[https://blog.csdn.net/guizhou_tiger_chen/article/details/108336508]
        mViewData?.rvCategory?.adapter = null
        super.onDestroyView()
    }

//    val args : CategoryFragmentArgs by navArgs()

    override fun initView(view: View, savedInstanceState: Bundle?) {
//        ALog.e("开始初始化了",args.pageType)
        val adapter= PagingSimpleAdapter()
        mViewData?.apply {
            rvCategory.adapter = adapter
        }

        mViewModel?.apply {
            getData().observe(viewLifecycleOwner) {
                adapter.submitData(viewLifecycleOwner.lifecycle, it)
            }

        }

    }




}