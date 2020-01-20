package com.soushin.tinmvvm.mvvm.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.base.BaseFragment
import com.soushin.tinmvvm.databinding.FragmentCategoryBinding
import com.soushin.tinmvvm.mvvm.viewmodel.CategoryViewModel

/**
 * @author created by Soushin
 * @time 2020/1/14 15 25
 */
class CategoryFragment : BaseFragment<FragmentCategoryBinding, CategoryViewModel>() {

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun initVariableId(): Int {
        return BR.categoryViewModel
    }


}