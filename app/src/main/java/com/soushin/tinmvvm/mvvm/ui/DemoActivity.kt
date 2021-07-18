package com.soushin.tinmvvm.mvvm.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.viewpager2.widget.ViewPager2
import com.blankj.ALog
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.app.getThis
import com.soushin.tinmvvm.databinding.ActivityDemoBinding
import com.soushin.tinmvvm.mvvm.adapter.ViewPager2StateAdapter
import com.soushin.tinmvvm.mvvm.ui.fragment.*
import com.soushin.tinmvvm.mvvm.viewmodel.DemoViewModel
import me.soushin.tinmvvm.base.DataBindingActivity
import me.soushin.tinmvvm.base.DataBindingFragment
import me.soushin.tinmvvm.config.DataBindingConfig

/**
 * 主页
 * https://juejin.im/post/5da90c54f265da5b932e7960  学习的网址
 * @author Soushin
 * @time 2020/1/7 13:39
 */
class DemoActivity : DataBindingActivity<ActivityDemoBinding, DemoViewModel>() {

    /*private val navHostFragment by lazy { supportFragmentManager.findFragmentById(R.id.nav_main_delegate) as NavHostFragment }
    private val navController by lazy { navHostFragment.navController }
    private val appBarConfiguration by lazy { AppBarConfiguration.Builder(R.id.homeFragment).build() }//配置homeFragment为顶部页面*/

    override fun initView(savedInstanceState: Bundle?) {
        mViewData?.apply {
            /*setSupportActionBar(toolbar)
            navController.setGraph(R.navigation.nav_main_delegate)
            NavigationUI.setupWithNavController(toolbar,navController)*/
            val fragments = mutableListOf<Fragment>(
                MainDelegateFragment.newInstance(R.navigation.nav_home),
                MainDelegateFragment.newInstance(R.navigation.nav_component),
                MainDelegateFragment.newInstance(R.navigation.nav_mine))
            val itemIds= mutableListOf<Int>(R.id.homeFragment,R.id.componentFragment,R.id.mineFragment)
            vpMainDelegate.adapter= ViewPager2StateAdapter(getThis(),fragments)
            bnvMainDelegate.setOnNavigationItemSelectedListener {
                vpMainDelegate.currentItem = itemIds.indexOf(it.itemId)
                return@setOnNavigationItemSelectedListener true
            }

            vpMainDelegate.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    bnvMainDelegate.selectedItemId = itemIds[position]
                }
            })
        }
    }

    /*override fun onSupportNavigateUp(): Boolean {
        // 配置NavController对后退事件进行控制
        return NavigationUI.navigateUp(navController,appBarConfiguration)
    }*/

    //配置当前页面的内容 各项参数都可为空
    //BR.xxxxViewModel是kotlin-kapt插件默认生成的 对应xml文件里的xxxxViewModel
    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(layoutId = R.layout.activity_demo,variableId = BR.demoViewModel,vmClass = DemoViewModel::class.java)
    }

    override fun onBackPressed() {
        val navController = Navigation.findNavController(getThis(),R.id.nav_main_delegate)
        if (!navController.navigateUp()){
            ALog.i("popBackStack",false,);
            moveTaskToBack(true)
//            super.onBackPressed()
        }
    }

}


fun DataBindingFragment<*,*>.nav():NavController{
    return Navigation.findNavController(requireActivity(),R.id.nav_main_delegate)
}
