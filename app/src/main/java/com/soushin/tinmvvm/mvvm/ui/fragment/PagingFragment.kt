package com.soushin.tinmvvm.mvvm.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blankj.ALog
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.app.getThis
import com.soushin.tinmvvm.app.showToasty
import com.soushin.tinmvvm.databinding.FragmentPagingBinding
import com.soushin.tinmvvm.mvvm.adapter.PagingSimpleAdapter
import com.soushin.tinmvvm.mvvm.viewmodel.PagingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
        mViewData?.apply {
            srlRefresh.setOnRefreshListener { adapter.refresh() }
            rvPaging.adapter = adapter
        }

        mViewModel?.getData()?.observe(getThis(),{
            adapter.submitData(lifecycle,it)
        })
       /*lifecycleScope.launch {
           mViewModel?.getData()?.collectLatest {
               adapter.submitData(it)
           }
       }*/


    }

}