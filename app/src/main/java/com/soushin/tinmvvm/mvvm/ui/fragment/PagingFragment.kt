package com.soushin.tinmvvm.mvvm.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.blankj.ALog
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.R
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

    override fun initView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) {
        val adapter = PagingSimpleAdapter()
        adapter.addLoadStateListener { //加载状态监听
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
        mViewData?.rvPaging?.adapter = adapter
        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                mViewModel?.getData()?.collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }

}