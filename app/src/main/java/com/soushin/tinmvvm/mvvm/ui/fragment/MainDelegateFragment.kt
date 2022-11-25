package com.soushin.tinmvvm.mvvm.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import com.blankj.ALog
import com.jeremyliao.liveeventbus.LiveEventBus
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.app.AppData
import com.soushin.tinmvvm.app.GlobalConstants
import com.soushin.tinmvvm.app.getThis
import com.soushin.tinmvvm.app.utils.FragmentUtils
import com.soushin.tinmvvm.databinding.FragmentMainDelegateBinding
import com.soushin.tinmvvm.mvvm.repository.entity.ViewTaskEvent
import com.soushin.tinmvvm.mvvm.viewmodel.MainDelegateViewModel
import me.soushin.tinmvvm.base.DataBindingFragment
import me.soushin.tinmvvm.config.DataBindingConfig

/**
 * 主页功能模块代理
 * @auther SouShin
 * @time 2021/7/19 11:37
 */
class MainDelegateFragment :
    DataBindingFragment<FragmentMainDelegateBinding, MainDelegateViewModel>() {
    companion object {
        fun newInstance(): MainDelegateFragment {
            return MainDelegateFragment()
        }
    }

    //配置当前页面的内容 各项参数都可为空
    //BR.xxxxViewModel是kotlin-kapt插件默认生成的 对应xml文件里的xxxxViewModel
    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(
            layoutId = R.layout.fragment_main_delegate, variableId = BR.MainDelegateViewModel,
            vmClass = MainDelegateViewModel::class.java
        )
    }

    override fun initView(view:View, savedInstanceState: Bundle?) {
        mViewData?.apply {

            val fragments = mutableListOf<Fragment>(HomeFragment.newInstance(),ComponentFragment.newInstance(),MineFragment.newInstance())
            FragmentUtils.add(childFragmentManager,fragments,R.id.fcv_main_delegate,0)

            bnvMainDelegate.setOnItemSelectedListener {
                when(it.itemId){
                    R.id.homeFragment->{
                        FragmentUtils.showHide(0,fragments)
                    }
                    R.id.componentFragment->{
                        FragmentUtils.showHide(1,fragments)
                    }
                    R.id.mineFragment->{
                        FragmentUtils.showHide(2,fragments)
                    }
                }
                return@setOnItemSelectedListener true
            }
        }
    }






}