package ${fragmentPackageName}

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ${packageName}.R
import ${packageName}.BR
import ${packageName}.base.BaseFragment
import ${packageName}.databinding.Activity${pageName}Binding
import ${packageName}.mvvm.viewmodel.${pageName}ViewModel


<#import "root://activities/TinMvvmTemplate/globals.xml.ftl" as gb>
<@gb.fileHeader />

class ${pageName}Fragment : BaseFragment<Activity${pageName}Binding,${pageName}ViewModel>() {
    companion object {
    fun newInstance():${pageName}Fragment {
        val fragment = ${pageName}Fragment()
        return fragment
        }
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):View{
        return inflater.inflate(R.layout.${fragmentLayoutName}, container, false);
    }

    override fun initData(savedInstanceState:Bundle?) {

    }

    override fun initVariableId(): Int {
        return BR.${pageName}ViewModel
    }

}
