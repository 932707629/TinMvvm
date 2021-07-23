package com.soushin.tinmvvm.mvvm.ui

import android.os.Bundle
import androidx.paging.LoadState
import com.blankj.ALog
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.app.getThis
import com.soushin.tinmvvm.app.showToasty
import com.soushin.tinmvvm.databinding.ActivitySplashBinding
import com.soushin.tinmvvm.mvvm.adapter.PagingSimpleAdapter
import com.soushin.tinmvvm.mvvm.viewmodel.SplashViewModel
import me.soushin.tinmvvm.base.DataBindingActivity
import me.soushin.tinmvvm.config.DataBindingConfig

class SplashActivity : DataBindingActivity<ActivitySplashBinding, SplashViewModel>() {

    //配置当前页面的内容 各项参数都可为空
    //BR.xxxxViewModel是kotlin-kapt插件默认生成的 对应xml文件里的xxxxViewModel
    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(
            layoutId = R.layout.activity_splash, variableId = BR.SplashViewModel,
            vmClass = SplashViewModel::class.java
        )
    }

    override fun initView(savedInstanceState: Bundle?) {
        val adapter = PagingSimpleAdapter()

        adapter.addLoadStateListener { //加载状态监听
            ALog.i("加载状态监听",it.mediator,it.prepend,it.refresh,it.source);
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
            rvPaging.adapter = adapter
        }

        mViewModel?.getData()?.observe(this,{
            adapter.submitData(getThis().lifecycle,it)
        })
        /*mViewData?.ivSplash?.let {iv->
            Glide.with(getThis())
                .load("https://wx2.sinaimg.cn/mw690/002Po4pSly1gsdxnl2grvj60u0190qvg02.jpg")
                .into(iv)
        }*/
    }

}