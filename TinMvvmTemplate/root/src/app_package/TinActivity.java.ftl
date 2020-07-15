package ${ativityPackageName};

import android.os.Bundle;

import me.soushin.tinmvvm.base.BaseActivity;
import ${packageName}.databinding.Activity${pageName}Binding;
import ${packageName}.mvvm.viewmodel.${pageName}ViewModel;

import org.jetbrains.annotations.Nullable;
import ${packageName}.R;
import ${packageName}.BR;

<#import "root://activities/TinMvvmTemplate/globals.xml.ftl" as gb>
<@gb.fileHeader />

public class ${pageName}Activity extends BaseActivity<Activity${pageName}Binding,${pageName}ViewModel> {

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.${activityLayoutName}; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

  @Override
    public int initVariableId() {
        return  BR.${pageName}ViewModel;//这里是为了绑定ViewModel用的 如果不需要请返回0
    }

}
