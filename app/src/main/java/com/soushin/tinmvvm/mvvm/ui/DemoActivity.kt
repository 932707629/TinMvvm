package com.soushin.tinmvvm.mvvm.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.view.forEach
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.blankj.ALog
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.app.AppData
import com.soushin.tinmvvm.databinding.ActivityDemoBinding
import com.soushin.tinmvvm.mvvm.ui.fragment.*
import com.soushin.tinmvvm.mvvm.viewmodel.DemoViewModel
import me.soushin.tinmvvm.base.DataBindingActivity
import me.soushin.tinmvvm.config.DataBindingConfig

/**
 * 主页
 * @author Soushin
 * @time 2020/1/7 13:39
 */
class DemoActivity : DataBindingActivity<ActivityDemoBinding, DemoViewModel>() {

    private val navHostFragment by lazy { supportFragmentManager.findFragmentById(R.id.fcv_container) as NavHostFragment }
    private val navController by lazy { navHostFragment.navController }
//    private val appBarConfiguration by lazy { AppBarConfiguration.Builder(R.id.homeFragment).build() }//配置homeFragment为顶部页面

    //主页点回退 将app任务移动到后台
    override fun onBackPressed() {
//        super.onBackPressed()
        moveTaskToBack(true)
    }

    override fun initView(savedInstanceState: Bundle?) {
        mViewData?.apply {
            setSupportActionBar(toolbar)
            toolbar.setOnMenuItemClickListener {
                onMenuItemClick(it)
                return@setOnMenuItemClickListener true
            }
            NavigationUI.setupWithNavController(bnvMainDelegate,navController)
            //监听页面转场 设置Toolbar以及BottomNavigationView的一些操作
            toolbar.setNavigationOnClickListener {
                navController.navigateUp()
            }

            navController.addOnDestinationChangedListener { controller, destination, arguments ->
                ALog.i("监听堆栈跳转", destination.toString(), arguments);
                mViewData?.apply {
                    toolbar.title = destination.label
                    val visible =
                        destination.id == R.id.homeFragment || destination.id == R.id.componentFragment
                                || destination.id == R.id.mineFragment
                    bnvMainDelegate.visibility = if (visible) View.VISIBLE else View.GONE
                    if (visible) toolbar.navigationIcon =
                        null else toolbar.setNavigationIcon(R.drawable.ic_white_arrow_left_24)
                }
            }
        }
    }

    //配置当前页面的内容 各项参数都可为空
    //BR.xxxxViewModel是kotlin-kapt插件默认生成的 对应xml文件里的xxxxViewModel
    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(layoutId = R.layout.activity_demo,
            variableId = BR.demoViewModel,vmClass = DemoViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_global,menu)
        return super.onCreateOptionsMenu(menu)
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





