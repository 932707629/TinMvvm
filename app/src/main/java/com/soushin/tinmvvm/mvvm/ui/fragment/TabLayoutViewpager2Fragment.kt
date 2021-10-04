package com.soushin.tinmvvm.mvvm.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.app.getThis
import com.soushin.tinmvvm.databinding.FragmentTablayoutViewpager2Binding
import com.soushin.tinmvvm.mvvm.adapter.ViewPager2StateAdapter
import com.soushin.tinmvvm.mvvm.viewmodel.TabLayoutViewpager2ViewModel
import me.soushin.tinmvvm.base.DataBindingFragment
import me.soushin.tinmvvm.config.DataBindingConfig

/**
 * TabLayoutViewPager2功能示例
 * @auther SouShin
 * @time 2021/7/14 14:53
 */
class TabLayoutViewpager2Fragment :
    DataBindingFragment<FragmentTablayoutViewpager2Binding, TabLayoutViewpager2ViewModel>() {
    companion object {
        fun newInstance(): TabLayoutViewpager2Fragment {
            return TabLayoutViewpager2Fragment()
        }
    }

    //配置当前页面的内容 各项参数都可为空
    //BR.xxxxViewModel是kotlin-kapt插件默认生成的 对应xml文件里的xxxxViewModel
    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(layoutId = R.layout.fragment_tablayout_viewpager2,variableId = BR.TablayoutViewpager2ViewModel,
            vmClass = TabLayoutViewpager2ViewModel::class.java)
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        mViewData?.apply {
            val fragments = mutableListOf<Fragment>(RecyclerFragment.newInstance(),
                RecyclerFragment.newInstance(),RecyclerFragment.newInstance())
            viewPager2.adapter=ViewPager2StateAdapter(getThis(),fragments)
            TabLayoutMediator(tabLayout,viewPager2){tab,position->
                tab.text = "OBJECT ${(position + 1)}"
            }.attach()
        }
    }

}








