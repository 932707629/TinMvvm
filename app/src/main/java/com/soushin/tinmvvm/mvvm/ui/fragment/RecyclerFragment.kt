package com.soushin.tinmvvm.mvvm.ui.fragment

import android.os.Bundle
import com.chad.library.adapter.base.BaseBinderAdapter
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.databinding.FragmentRecyclerBinding
import com.soushin.tinmvvm.mvvm.adapter.itembinder.ImageItemBinder
import com.soushin.tinmvvm.mvvm.adapter.itembinder.TextItemBinder
import com.soushin.tinmvvm.mvvm.model.entity.ImageEntity
import com.soushin.tinmvvm.mvvm.viewmodel.RecyclerViewModel
import com.soushin.tinmvvm.utils.DataUtils
import me.soushin.tinmvvm.base.BaseFragment

/**
 * ================================================
 * Description:
 * <p>
 * Created by TinMvvmTemplate on 03/02/2020 16:36
 * ================================================
 */

class RecyclerFragment : BaseFragment<FragmentRecyclerBinding, RecyclerViewModel>() {

    val adapter:BaseBinderAdapter= BaseBinderAdapter()

    override fun initView(savedInstanceState: Bundle?) {
        viewData?.apply {
            srlRefresh.setOnRefreshListener {
                adapter.setList(DataUtils.getRecyclerData())
                srlRefresh.isRefreshing=false
            }
            adapter.addItemBinder(ImageEntity::class.java,ImageItemBinder())
                .addItemBinder(String::class.java,TextItemBinder())

            //LinearLayoutManager已经在布局文件里设置了 所以这里只要设置adapter就可以了
            rvRecycler.adapter=adapter

            adapter.setList(DataUtils.getRecyclerData())
        }
    }

    override fun initVariableId(): Int {
        return BR.RecyclerViewModel;//这里是为了绑定ViewModel用的 如果不需要请返回0
    }


}
