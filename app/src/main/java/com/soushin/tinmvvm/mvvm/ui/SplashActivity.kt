package com.soushin.tinmvvm.mvvm.ui

import android.os.Bundle;
import com.soushin.tinmvvm.BR;
import com.soushin.tinmvvm.R;
import com.soushin.tinmvvm.databinding.ActivitySplashBinding;
import com.soushin.tinmvvm.mvvm.viewmodel.SplashViewModel;
import me.soushin.tinmvvm.base.DataBindingActivity;
import me.soushin.tinmvvm.config.DataBindingConfig;

class SplashActivity : DataBindingActivity<ActivitySplashBinding, SplashViewModel>() {

    //配置当前页面的内容 各项参数都可为空
    //BR.xxxxViewModel是kotlin-kapt插件默认生成的 对应xml文件里的xxxxViewModel
    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(
            layoutId = R.layout.activity_splash, variableId = BR.SplashViewModel,
            vmClass = SplashViewModel::class.java
        );
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

}