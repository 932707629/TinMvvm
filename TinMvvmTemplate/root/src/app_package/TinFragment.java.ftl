package ${fragmentPackageName};

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ${packageName}.base.BaseFragment;
import ${packageName}.databinding.Fragment${pageName}Binding;
import ${packageName}.mvvm.viewmodel.${pageName}ViewModel;

import androidx.annotation.NonNull;
import org.jetbrains.annotations.Nullable;

import ${packageName}.R;
import ${packageName}.BR;

<#import "root://activities/TinMvvmTemplate/globals.xml.ftl" as gb>
<@gb.fileHeader />

public class ${pageName}Fragment extends BaseFragment<Fragment${pageName}Binding,${pageName}ViewModel>{

    public static ${pageName}Fragment newInstance() {
        ${pageName}Fragment fragment = new ${pageName}Fragment();
        return fragment;
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.${fragmentLayoutName}, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public int initVariableId() {
        return  BR.${pageName}ViewModel;//这里是为了绑定ViewModel用的 如果不需要请返回0
    }

}
