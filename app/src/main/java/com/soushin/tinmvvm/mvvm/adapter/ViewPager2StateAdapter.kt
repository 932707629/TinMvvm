package com.soushin.tinmvvm.mvvm.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.blankj.ALog

class ViewPager2StateAdapter:FragmentStateAdapter {
    private var fragments :MutableList<Fragment>?=null

    constructor(fragmentActivity: FragmentActivity,fragments :MutableList<Fragment>) : super(fragmentActivity){
        this.fragments=fragments
    }
    constructor(fragment: Fragment,fragments :MutableList<Fragment>) : super(fragment){
        this.fragments=fragments
    }
    constructor(fragmentManager: FragmentManager, lifecycle: Lifecycle,
                fragments :MutableList<Fragment>) : super(fragmentManager, lifecycle){
        this.fragments=fragments
    }


    override fun getItemCount(): Int = fragments?.size?:0


    override fun createFragment(position: Int): Fragment {
        ALog.i("当前位置打印了",position);
        return fragments!![position]
    }


}