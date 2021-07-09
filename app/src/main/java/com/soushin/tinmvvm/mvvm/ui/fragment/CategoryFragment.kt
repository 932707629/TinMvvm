package com.soushin.tinmvvm.mvvm.ui.fragment

import android.os.Bundle
import androidx.navigation.Navigation
import com.blankj.ALog
import com.jeremyliao.liveeventbus.LiveEventBus
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.databinding.FragmentCategoryBinding
import com.soushin.tinmvvm.mvvm.viewmodel.CategoryViewModel
import com.soushin.tinmvvm.utils.FragmentUtils
import me.soushin.tinmvvm.base.BaseFragment

/**
 * @author created by Soushin
 * @time 2020/1/14 15 25
 */
class CategoryFragment : BaseFragment<FragmentCategoryBinding, CategoryViewModel>() {

    override fun initView(savedInstanceState: Bundle?) {
        ALog.e("开始初始化了")
    }

    override fun initVariableId(): Int {
        return BR.categoryViewModel
    }


}