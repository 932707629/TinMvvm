package com.soushin.tinmvvm.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

/**
 * @author created by Soushin
 * @time 2020/1/7 15 23
 */
public class Base <VM extends ViewModel> extends AppCompatActivity {

    private VM viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel= (VM) ViewModelProviders.of(this).get(viewModel.getClass());
    }



}
