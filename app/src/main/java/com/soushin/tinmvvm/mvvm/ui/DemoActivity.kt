package com.soushin.tinmvvm.mvvm.ui

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.databinding.ActivityDemoBinding
import com.soushin.tinmvvm.mvvm.viewmodel.DemoViewModel
import me.soushin.tinmvvm.base.BaseActivity

/**
 * 主页
 * https://juejin.im/post/5da90c54f265da5b932e7960  学习的网址
 * @author Soushin
 * @time 2020/1/7 13:39
 */
class DemoActivity : BaseActivity<ActivityDemoBinding, DemoViewModel>() {

    private val navHostFragment by lazy { supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment }
    private val navController by lazy { navHostFragment.navController }
    private val appBarConfiguration by lazy { AppBarConfiguration.Builder(R.id.homeFragment).build() }//配置homeFragment为顶部页面

    override fun initView(savedInstanceState: Bundle?) {
        mViewData?.apply {
            setSupportActionBar(toolbar)
            NavigationUI.setupWithNavController(toolbar,navController)
            /*navController.navigatorProvider.addNavigator(FixFragmentNavigator(getThis(),
                navHostFragment.childFragmentManager,navHostFragment.id))
            navController.setGraph(R.navigation.nav_main)*/
        }
    }

    //BR.xxxxViewModel是kotlin-kapt插件默认生成的 对应xml文件里的xxxxViewModel
    override fun initVariableId(): Int {
        return BR.demoViewModel
    }

    override fun onSupportNavigateUp(): Boolean {
        // 配置NavController对后退事件进行控制
        return NavigationUI.navigateUp(navController,appBarConfiguration)
    }




}



