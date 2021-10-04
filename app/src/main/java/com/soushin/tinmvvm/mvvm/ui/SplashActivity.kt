package com.soushin.tinmvvm.mvvm.ui

import android.os.Bundle
import com.blankj.ALog
import com.bumptech.glide.Glide
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.app.getThis
import com.soushin.tinmvvm.databinding.ActivitySplashBinding
import com.soushin.tinmvvm.mvvm.viewmodel.ComponentViewModel
import com.soushin.tinmvvm.mvvm.viewmodel.MineViewModel
import com.soushin.tinmvvm.mvvm.viewmodel.SplashViewModel
import me.soushin.tinmvvm.base.DataBindingActivity
import me.soushin.tinmvvm.config.DataBindingConfig
import kotlin.math.min

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
        mViewData?.ivSplash?.let {iv->
            Glide.with(getThis())
                .load("https://wx2.sinaimg.cn/mw690/002Po4pSly1gsdxnl2grvj60u0190qvg02.jpg")
                .into(iv)
        }

        val componentVM = mViewModelProvider?.get(ComponentViewModel::class.java)
        ALog.i("打印取到的上个页面的数据",componentVM?.viewEvent?.value);

    }

}