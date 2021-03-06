package ${ativityPackageName}

import android.os.Bundle

import ${packageName}.R
import ${packageName}.BR
import me.soushin.tinmvvm.base.BaseActivity
import ${packageName}.databinding.Activity${pageName}Binding
import ${packageName}.mvvm.viewmodel.${pageName}ViewModel

<#import "root://activities/TinMvvmTemplate/globals.xml.ftl" as gb>
<@gb.fileHeader />

class ${pageName}Activity : BaseActivity<Activity${pageName}Binding,${pageName}ViewModel>()  {


    override fun initView(savedInstanceState:Bundle?):Int {
        return R.layout.${activityLayoutName} //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    override fun initData(savedInstanceState:Bundle?) {

    }

    override fun initVariableId(): Int {
        return  BR.${pageName}ViewModel;//这里是为了绑定ViewModel用的 如果不需要请返回0
    }



}
