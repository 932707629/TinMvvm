package com.soushin.tinmvvm.mvvm.viewmodel

import android.app.Application
import com.soushin.tinmvvm.base.BaseViewModel
import com.soushin.tinmvvm.mvvm.model.CategoryModel

/**
 * @author created by Soushin
 * @time 2020/1/14 18 05
 */
class CategoryViewModel: BaseViewModel<CategoryModel> {

    constructor(application: Application):super(application, CategoryModel()){

    }

}