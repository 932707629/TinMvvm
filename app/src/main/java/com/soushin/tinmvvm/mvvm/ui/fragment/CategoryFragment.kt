package com.soushin.tinmvvm.mvvm.ui.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import com.blankj.ALog
import com.jeremyliao.liveeventbus.LiveEventBus
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.databinding.FragmentCategoryBinding
import com.soushin.tinmvvm.mvvm.viewmodel.CategoryViewModel
import com.soushin.tinmvvm.utils.FragmentUtils
import me.soushin.tinmvvm.base.BaseFragment
import me.soushin.tinmvvm.utils.onClick

/**
 * @author created by Soushin
 * @time 2020/1/14 15 25
 */
class CategoryFragment : BaseFragment<FragmentCategoryBinding, CategoryViewModel>() {

    override fun initView(savedInstanceState: Bundle?) {
        ALog.e("开始初始化了")
        viewModel?.pageSkip?.observe(this, {
            if (it==2){//返回上一页
                FragmentUtils.removeFragment(this)
            }else {
                LiveEventBus.get<Int>("pageChange").post(it)
            }
        })
    }

    override fun initVariableId(): Int {
        return BR.categoryViewModel
    }


}