package me.soushin.tinmvvm.mvvm.ui

import android.os.Bundle
import cat.ereza.customactivityoncrash.CustomActivityOnCrash
import com.gyf.immersionbar.ImmersionBar
import me.soushin.tinmvvm.BR
import me.soushin.tinmvvm.base.BaseActivity
import me.soushin.tinmvvm.config.AppComponent
import me.soushin.tinmvvm.databinding.ActivityTinErrorBinding
import me.soushin.tinmvvm.mvvm.viewmodel.TinErrorViewModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by TinMvvmTemplate on 07/24/2020 16:35
 * ================================================
 */

class TinErrorActivity : BaseActivity<ActivityTinErrorBinding, TinErrorViewModel>() {


    override fun initView(savedInstanceState: Bundle?): Int {
        return 0 //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    override fun initData(savedInstanceState: Bundle?) {
        ImmersionBar.with(this).init()
        //配置信息
//        val config= CustomActivityOnCrash.getConfigFromIntent(intent)
        //异常信息  无法捕获ANR异常信息
        val error= CustomActivityOnCrash.getStackTraceFromIntent(intent)
        //把这个activity回调给开发者 后续怎么处理交给开发者决定
        AppComponent.globalConfig?.exceptionCallBack?.exceptionCallback(this,error?:"")
    }

    override fun initVariableId(): Int {
        return BR.TinErrorViewModel;//这里是为了绑定ViewModel用的 如果不需要请返回0
    }


}
