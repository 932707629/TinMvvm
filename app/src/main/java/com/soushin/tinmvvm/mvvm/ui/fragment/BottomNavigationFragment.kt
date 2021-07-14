package com.soushin.tinmvvm.mvvm.ui.fragment

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment

import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.BR
import me.soushin.tinmvvm.base.BaseFragment
import com.soushin.tinmvvm.databinding.FragmentBottomNavigationBinding
import com.soushin.tinmvvm.mvvm.viewmodel.BottomNavigationViewModel

/**
 * 演示BottomNavigationView使用
 * @auther SouShin
 * @time 2021/7/14 11:13
 */
class BottomNavigationFragment :
    BaseFragment<FragmentBottomNavigationBinding, BottomNavigationViewModel>() {
    companion object {
        fun newInstance(): BottomNavigationFragment {
            return BottomNavigationFragment()
        }
    }
    private val navHostFragment by lazy { childFragmentManager.findFragmentById(R.id.nav_host_bottom) as NavHostFragment }
    private val navController by lazy { navHostFragment.navController }

    override fun initView(savedInstanceState: Bundle?) {
        viewData?.apply {
//            bnvNavigation.setupWithNavController(navController)
            /**因为用了保存fragment状态的方式 所以如果用BottomNavigationView的时候需要用下面的方式处理
            如果用原生的需要把nav_main.xml文件中的fixfragment替换为fragment 然后取消上面代码注释即可*/
//            val navController = Navigation.findNavController(bnvNavigation)
            bnvNavigation.setOnNavigationItemSelectedListener {
                if (bnvNavigation.selectedItemId == it.itemId) return@setOnNavigationItemSelectedListener true
                when (it.itemId) {
                    R.id.recyclerFragment -> {
                        if (navController.currentDestination?.id == R.id.bottomNavigationFragment){
                            navController.navigate(R.id.action_bottomNavigationFragment_to_recyclerFragment)
                        }else {
                            navController.navigateUp()
                        }
                    }
                    R.id.multiplexFragment -> {
                        if (navController.currentDestination?.id == R.id.bottomNavigationFragment){
                            navController.navigate(R.id.action_bottomNavigationFragment_to_multiplexFragment)
                        }else {
                            navController.navigateUp()
                        }
                    }
                    else -> {}
                }
                return@setOnNavigationItemSelectedListener true
            }
        }
    }

    override fun initVariableId(): Int {
        return BR.BottomNavigationViewModel;//这里是为了绑定ViewModel用的 如果不需要请返回0
    }
}