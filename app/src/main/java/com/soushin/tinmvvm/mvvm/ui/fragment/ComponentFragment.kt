package com.soushin.tinmvvm.mvvm.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseBinderAdapter
import com.google.android.flexbox.*
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.app.getThis
import com.soushin.tinmvvm.databinding.FragmentComponentBinding
import com.soushin.tinmvvm.mvvm.adapter.itembinder.TabComponentItemBinder
import com.soushin.tinmvvm.mvvm.viewmodel.ComponentViewModel
import me.soushin.tinmvvm.base.DataBindingFragment
import me.soushin.tinmvvm.config.DataBindingConfig

class ComponentFragment : DataBindingFragment<FragmentComponentBinding, ComponentViewModel>() {
    companion object {
        fun newInstance(): ComponentFragment {
            return ComponentFragment()
        }
    }

    //配置当前页面的内容 各项参数都可为空
    //BR.xxxxViewModel是kotlin-kapt插件默认生成的 对应xml文件里的xxxxViewModel
    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(
            layoutId = R.layout.fragment_component, variableId = BR.ComponentViewModel,
            vmClass = ComponentViewModel::class.java
        )
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        mViewData?.apply {
            val layoutManager = FlexboxLayoutManager(requireContext())
            //主轴为水平方向，起点在左端
            layoutManager.flexDirection = FlexDirection.ROW;
            //按正常方向换行
            layoutManager.flexWrap = FlexWrap.WRAP;
            //定义项目在副轴轴上如何对齐
            layoutManager.alignItems = AlignItems.CENTER;
            //多个轴对齐方式
            layoutManager.justifyContent = JustifyContent.FLEX_START;
            rvComponent.layoutManager = layoutManager
            val adapter = BaseBinderAdapter()
            adapter.addItemBinder(TabComponentItemBinder())
            adapter.setOnItemClickListener { ada, view, position ->
                mViewModel?.onItemClick(ada.getItem(position) as String,view,position)
            }
            rvComponent.adapter=adapter
            mViewModel?.loadData()
            mViewModel?.viewEvent?.observe(getThis(),{
                adapter.setList(it)
            })
        }
    }

}