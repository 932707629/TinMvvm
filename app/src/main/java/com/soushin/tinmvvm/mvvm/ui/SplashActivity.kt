package com.soushin.tinmvvm.mvvm.ui

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.blankj.ALog
import com.bumptech.glide.Glide
import com.jeremyliao.liveeventbus.LiveEventBus
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.app.GlobalConstants
import com.soushin.tinmvvm.app.getThis
import com.soushin.tinmvvm.databinding.ActivitySplashBinding
import com.soushin.tinmvvm.mvvm.repository.entity.ViewTaskEvent
import com.soushin.tinmvvm.mvvm.viewmodel.ComponentViewModel
import com.soushin.tinmvvm.mvvm.viewmodel.SplashViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.soushin.tinmvvm.base.DataBindingActivity
import me.soushin.tinmvvm.config.DataBindingConfig
import me.soushin.tinmvvm.utils.AppManager

class SplashActivity : DataBindingActivity<ActivitySplashBinding, ComponentViewModel>() {

    //配置当前页面的内容 各项参数都可为空
    //BR.xxxxViewModel是kotlin-kapt插件默认生成的 对应xml文件里的xxxxViewModel
    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(
            layoutId = R.layout.activity_splash, variableId = BR.ComponentViewModel,
            vmClass = ComponentViewModel::class.java
        )
    }

    override fun initView(savedInstanceState: Bundle?) {
        mViewData?.ivSplash?.let {iv->
            Glide.with(getThis())
                .load("https://wx2.sinaimg.cn/mw690/002Po4pSly1gsdxnl2grvj60u0190qvg02.jpg")
                .into(iv)
        }

        val componentVM = mViewModelProvider?.get(ComponentViewModel::class.java)
        ALog.i("打印取到的上个页面的数据",componentVM?.viewEvent?.value,mViewModel?.viewEvent?.value);


    }

}