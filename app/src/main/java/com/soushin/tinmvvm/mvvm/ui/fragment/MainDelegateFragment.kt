package com.soushin.tinmvvm.mvvm.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.blankj.ALog
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.app.LiveDataTag
import com.soushin.tinmvvm.databinding.FragmentMainDelegateBinding
import com.soushin.tinmvvm.mvvm.viewmodel.MainDelegateViewModel
import me.soushin.tinmvvm.base.DataBindingFragment
import me.soushin.tinmvvm.config.DataBindingConfig

class MainDelegateFragment :
    DataBindingFragment<FragmentMainDelegateBinding, MainDelegateViewModel>() {
    companion object {
        fun newInstance(graphId: Int): MainDelegateFragment {
            return MainDelegateFragment().apply {
                arguments = Bundle().apply {
                    putInt(LiveDataTag.tag_main_delegate_graph, graphId)
                }
            }
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

    private val navHostFragment by lazy { childFragmentManager.findFragmentById(R.id.nav_main_delegate) as NavHostFragment }
    private val navController by lazy { navHostFragment.navController }
//    private val appBarConfiguration by lazy { AppBarConfiguration.Builder(R.id.homeFragment).build() }//配置homeFragment为顶部页面
    override fun initView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) {
        mViewData?.toolbar?.setNavigationOnClickListener { requireActivity().onBackPressed() }

        val graphId = requireArguments()[LiveDataTag.tag_main_delegate_graph] as Int
        navController.setGraph(graphId)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            ALog.i("监听堆栈跳转",destination.toString(),arguments);
            mViewData?.apply {
                toolbar.title = destination.label
                if (destination.id != R.id.homeFragment && destination.id != R.id.componentFragment
                    && destination.id != R.id.mineFragment){
                    toolbar.setNavigationIcon(R.drawable.ic_white_arrow_left_24)
                }else {
                    toolbar.navigationIcon = null
                }
            }
        }
    }


}