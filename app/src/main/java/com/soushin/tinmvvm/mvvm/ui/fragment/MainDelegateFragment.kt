package com.soushin.tinmvvm.mvvm.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.core.view.forEach
import com.blankj.ALog
import com.jeremyliao.liveeventbus.LiveEventBus
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.app.AppData
import com.soushin.tinmvvm.app.GlobalConstants
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
        fun newInstance(graphId: Int): MainDelegateFragment {
            return MainDelegateFragment().apply {
                arguments = Bundle().apply {
                    putInt(GlobalConstants.tag_main_delegate_graph, graphId)
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

//    private val navHostFragment by lazy { childFragmentManager.findFragmentById(R.id.fcv_main_delegate) as NavHostFragment }
//    private val navController by lazy { navHostFragment.navController }

    //    private val appBarConfiguration by lazy { AppBarConfiguration.Builder(R.id.homeFragment).build() }//配置homeFragment为顶部页面
    override fun initView(view:View, savedInstanceState: Bundle?) {
        mViewData?.apply {
            toolbar.inflateMenu(R.menu.menu_main_global)
            toolbar.setOnMenuItemClickListener {
                onMenuItemClick(it)
                return@setOnMenuItemClickListener true
            }
            toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        }
//        val graphId = requireArguments()[GlobalConstants.tag_main_delegate_graph] as Int
/*        navController.setGraph(graphId)
        //监听页面转场 设置Toolbar以及BottomNavigationView的一些操作
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            ALog.i("监听堆栈跳转", destination.toString(), arguments);
            mViewData?.apply {
                toolbar.title = destination.label
                val visible =
                    destination.id == R.id.homeFragment || destination.id == R.id.componentFragment
                            || destination.id == R.id.mineFragment
                LiveEventBus.get<ViewTaskEvent>(GlobalConstants.tag_main_view_event)
                    .post(ViewTaskEvent(key = 0, value = visible))
                if (visible) toolbar.navigationIcon =
                    null else toolbar.setNavigationIcon(R.drawable.ic_white_arrow_left_24)
            }
        }*/
    }

    private fun onMenuItemClick(it: MenuItem) {
        ALog.i("onMenuItemClick",it.itemId,it.groupId,it.order);
        if (it.groupId == R.id.group_single){
            AppData.get().saveNavOptions(it.itemId)
        } else if (it.itemId == R.id.menu_transitions_check) {
            it.subMenu.forEach {subItem->
                val defaultId = AppData.get().queryAnim()?:R.id.single_menu_non
                ALog.i("设置默认选择状态",subItem.title,subItem.itemId,defaultId);
                if (subItem.itemId == defaultId){
                    subItem.isChecked = true
                }
            }
        }
    }




}