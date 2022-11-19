package com.soushin.tinmvvm.mvvm.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import com.blankj.ALog
import com.jeremyliao.liveeventbus.LiveEventBus
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.app.AppData
import com.soushin.tinmvvm.app.GlobalConstants
import com.soushin.tinmvvm.app.getThis
import com.soushin.tinmvvm.app.utils.FragmentUtils
import com.soushin.tinmvvm.databinding.ActivityDemoBinding
import com.soushin.tinmvvm.mvvm.repository.entity.ViewTaskEvent
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

    //主页点回退 将app任务移动到后台
    override fun onBackPressed() {
        val fragmentStack = FragmentUtils.getFragments(supportFragmentManager)
        if (fragmentStack.size <= 1){
            moveTaskToBack(true)
        }else {
            FragmentUtils.remove(fragmentStack.last())
            fragmentStack.removeLast()
            FragmentUtils.show(fragmentStack.last())
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mViewData?.apply {
            FragmentUtils.add(supportFragmentManager,MainDelegateFragment.newInstance(),R.id.fcv_container,false,true)
        }

        LiveEventBus.get<ViewTaskEvent>(GlobalConstants.tag_main_view_event).observe(getThis()){
            when(it.key){
                GlobalConstants.action_add->{
                    FragmentUtils.hide(FragmentUtils.getFragments(supportFragmentManager).last())
                    val top = it.value as Fragment
                    FragmentUtils.add(supportFragmentManager,top,R.id.fcv_container,false,true)
                }
                GlobalConstants.action_back->{
                    onBackPressed()
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





