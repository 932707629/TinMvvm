package com.soushin.tinmvvm.mvvm.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator

import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.config.getThis
import me.soushin.tinmvvm.base.BaseFragment
import com.soushin.tinmvvm.databinding.FragmentTablayoutViewpager2Binding
import com.soushin.tinmvvm.mvvm.viewmodel.TablayoutViewpager2ViewModel

/**
 * TabLayoutViewPager2功能示例
 * @auther SouShin
 * @time 2021/7/14 14:53
 */
class TablayoutViewpager2Fragment :
    BaseFragment<FragmentTablayoutViewpager2Binding, TablayoutViewpager2ViewModel>() {
    companion object {
        fun newInstance(): TablayoutViewpager2Fragment {
            return TablayoutViewpager2Fragment()
        }
    }
    private val collectionAdapter by lazy { CollectionAdapter(getThis()) }

    override fun initView(savedInstanceState: Bundle?) {
        viewData?.apply {
            viewData?.apply {
                viewPager2.adapter=collectionAdapter
                TabLayoutMediator(tabLayout,viewPager2){tab,position->
                    tab.text = "OBJECT ${(position + 1)}"
                }.attach()
            }
        }
    }

    override fun initVariableId(): Int {
        return BR.TablayoutViewpager2ViewModel;//这里是为了绑定ViewModel用的 如果不需要请返回0
    }
}


class CollectionAdapter : FragmentStateAdapter {
    constructor(fragmentActivity: FragmentActivity) : super(fragmentActivity)
    constructor(fragment: Fragment) : super(fragment)
    constructor(fragmentManager: FragmentManager, lifecycle: Lifecycle) : super(
        fragmentManager,
        lifecycle
    )
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{
                MultiplexFragment.newInstance()
            }
            1->{
                RecyclerFragment.newInstance()
            }
            else->{
                RecyclerFragment.newInstance()
            }
        }
    }
}







