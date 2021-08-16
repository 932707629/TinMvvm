package com.soushin.tinmvvm.mvvm.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.blankj.ALog
import com.chad.library.adapter.base.BaseBinderAdapter
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.app.getThis
import com.soushin.tinmvvm.databinding.FragmentRecyclerBinding
import com.soushin.tinmvvm.mvvm.adapter.itembinder.ImageItemBinder
import com.soushin.tinmvvm.mvvm.adapter.itembinder.TextItemBinder
import com.soushin.tinmvvm.mvvm.viewmodel.RecyclerViewModel
import me.soushin.tinmvvm.base.DataBindingFragment
import me.soushin.tinmvvm.config.DataBindingConfig

/**
 * ================================================
 * Description:
 * <p>
 * Created by TinMvvmTemplate on 03/02/2020 16:36
 * ================================================
 */

class RecyclerFragment : DataBindingFragment<FragmentRecyclerBinding, RecyclerViewModel>() {

    override fun initView(view: View, savedInstanceState: Bundle?) {
        mViewData?.apply {
            ALog.i("RecyclerFragment",savedInstanceState);
            val adapter = BaseBinderAdapter().apply {
                addItemBinder(ImageItemBinder())
                addItemBinder(TextItemBinder())
            }
            adapter.setGridSpanSizeLookup { gridLayoutManager, viewType, position ->
//                ALog.i("打印数据类型",viewType,position);
                return@setGridSpanSizeLookup if (viewType == 1) 1 else 4
            }
            adapter.setOnItemClickListener { adapter, view, position ->
                if (findNavController().currentDestination?.id == R.id.recyclerFragment){
                    findNavController().navigate(RecyclerFragmentDirections.actionRecyclerFragmentToThreadPoolFragment())
                }
            }
            //LinearLayoutManager已经在布局文件里设置了 所以这里只要设置adapter就可以了
            rvRecycler.adapter=adapter
//            adapter.setList(DataUtils.getRecyclerData())
            mViewModel?.apply {
                loadData()
                viewEvent.observe(getThis(),{
                    if (it.value is MutableList<*>){
                        adapter.setList(it.value as MutableList<Any>)
                    }
                })
            }
        }
    }

    //配置当前页面的内容 各项参数都可为空
    //BR.xxxxViewModel是kotlin-kapt插件默认生成的 对应xml文件里的xxxxViewModel
    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(layoutId = R.layout.fragment_recycler,variableId = BR.RecyclerViewModel,
            vmClass = RecyclerViewModel::class.java)
    }

    companion object {
        fun newInstance(): RecyclerFragment {
            return RecyclerFragment()
        }
    }

}
