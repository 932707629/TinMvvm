package com.soushin.tinmvvm.mvvm.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.blankj.ALog
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.app.getThis
import com.soushin.tinmvvm.databinding.FragmentMultiplexBinding
import com.soushin.tinmvvm.mvvm.viewmodel.MultiplexViewModel
import me.soushin.tinmvvm.base.DataBindingFragment
import me.soushin.tinmvvm.config.DataBindingConfig

/**
 * ================================================
 * Description:复杂布局(可吸顶)
 * <p>
 * Created by TinMvvmTemplate on 01/20/2020 15:26
 * ================================================
 */
class MultiplexFragment : DataBindingFragment<FragmentMultiplexBinding, MultiplexViewModel>() {
    companion object {
        fun newInstance(): MultiplexFragment {
            return MultiplexFragment()
        }
    }
    private val strArray = arrayOf("关注", "推荐", "视频", "直播", "图片", "段子", "精华", "热门")

    override fun initView(view: View, savedInstanceState: Bundle?) {
        mViewData?.apply {
            Glide.with(getThis())
                .load("https://wx2.sinaimg.cn/mw690/002Po4pSly1grt79wku06j61jk0rskjl02.jpg").into(ivImage)
//            这种用法当fragment不会有内存泄露 虽然fragment很多 但是每次都是用的RecyclerFragment.newInstance()
            viewPager2.adapter = object :FragmentStateAdapter(getThis()){
                override fun getItemCount(): Int = strArray.size

                override fun createFragment(position: Int): Fragment {
                    return RecyclerFragment.newInstance()
                }
            }
            TabLayoutMediator(tabLayout,viewPager2){tab,position->
                tab.text = strArray[position]
            }.attach()
        }
    }

    //配置当前页面的内容 各项参数都可为空
    //BR.xxxxViewModel是kotlin-kapt插件默认生成的 对应xml文件里的xxxxViewModel
    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(layoutId = R.layout.fragment_multiplex,variableId = BR.MultiplexViewModel,
            vmClass = MultiplexViewModel::class.java)
    }
}
