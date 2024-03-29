package com.soushin.tinmvvm.mvvm.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.paging.LoadState
import com.blankj.ALog
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.app.showToasty
import com.soushin.tinmvvm.databinding.FragmentPagingBinding
import com.soushin.tinmvvm.mvvm.adapter.PagingSimpleAdapter
import com.soushin.tinmvvm.mvvm.viewmodel.PagingViewModel
import me.soushin.tinmvvm.base.DataBindingFragment
import me.soushin.tinmvvm.config.DataBindingConfig

class PagingFragment : DataBindingFragment<FragmentPagingBinding, PagingViewModel>() {
    companion object {
        fun newInstance(): PagingFragment {
            return PagingFragment()
        }
    }

    //配置当前页面的内容 各项参数都可为空
    //BR.xxxxViewModel是kotlin-kapt插件默认生成的 对应xml文件里的xxxxViewModel
    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(
            layoutId = R.layout.fragment_paging, variableId = BR.PagingViewModel,
            vmClass = PagingViewModel::class.java
        )
    }

    override fun onDestroyView() {
        //不置空会出现adapter内存泄露 详情[https://blog.csdn.net/guizhou_tiger_chen/article/details/108336508]
        mViewData?.rvPaging?.adapter = null
        super.onDestroyView()
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {

        mViewData?.apply {
            val adapter = PagingSimpleAdapter()
            adapter.addLoadStateListener { //加载状态监听
                ALog.i("加载状态监听",it.mediator,it.prepend,it.refresh,it.source,Thread.currentThread().name);
                if (mViewData?.srlRefresh?.isRefreshing == true){
                    mViewData?.srlRefresh?.isRefreshing=false
                }
                when (it.refresh) {
                    is LoadState.NotLoading -> {
                        showToasty("NotLoading")
                    }
                    is LoadState.Loading -> {
                        showToasty("Loading")
                    }
                    is LoadState.Error -> {
                        showToasty("Error")
                    }
                }
            }
            srlRefresh.setOnRefreshListener { adapter.refresh() }
            rvPaging.adapter = adapter

            mViewModel?.apply {
                getData().observe(viewLifecycleOwner,{
                    adapter.submitData(viewLifecycleOwner.lifecycle,it)
                })
            }
        }
    }

}