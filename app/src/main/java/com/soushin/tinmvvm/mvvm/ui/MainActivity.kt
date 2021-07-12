package com.soushin.tinmvvm.mvvm.ui

import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.config.getThis
import com.soushin.tinmvvm.databinding.ActivityMainBinding
import com.soushin.tinmvvm.mvvm.viewmodel.MainViewModel
import com.soushin.tinmvvm.widget.FixFragmentNavigator
import me.soushin.tinmvvm.base.BaseActivity
/**
 * 主页
 * https://juejin.im/post/5da90c54f265da5b932e7960  学习的网址
 * @author Soushin
 * @time 2020/1/7 13:39
 */
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    private val navController by lazy { Navigation.findNavController(getThis(), R.id.navHost) }
    private val appBarConfiguration by lazy { AppBarConfiguration.Builder(R.id.homeFragment).build() }//配置homeFragment为顶部页面

    override fun initView(savedInstanceState: Bundle?) {
        viewData?.apply {
            setSupportActionBar(toolbar)
            val fragment = supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
            navController.navigatorProvider.addNavigator(FixFragmentNavigator(getThis(),fragment.childFragmentManager,fragment.id))
            navController.setGraph(R.navigation.nav_main)
            NavigationUI.setupActionBarWithNavController(getThis(), navController, appBarConfiguration);
        }
    }

    //BR.xxxxViewModel是kotlin-kapt插件默认生成的 对应xml文件里的xxxxViewModel
    override fun initVariableId(): Int {
        return BR.mainViewModel
    }

    override fun onSupportNavigateUp(): Boolean {
        // 配置NavController对后退事件进行控制
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }



}
