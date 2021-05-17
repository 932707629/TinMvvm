package com.soushin.tinmvvm.mvvm.ui

import android.os.Bundle
import com.chad.library.adapter.base.BaseBinderAdapter
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.databinding.ActivityRecyclerBinding
import com.soushin.tinmvvm.mvvm.adapter.itembinder.ImageItemBinder
import com.soushin.tinmvvm.mvvm.adapter.itembinder.TextItemBinder
import com.soushin.tinmvvm.mvvm.model.entity.ImageEntity
import com.soushin.tinmvvm.mvvm.viewmodel.RecyclerViewModel
import com.soushin.tinmvvm.utils.DataUtils
import me.soushin.tinmvvm.base.BaseActivity

/**
 * ================================================
 * Description:
 * <p>
 * Created by TinMvvmTemplate on 03/02/2020 16:36
 * ================================================
 */

class RecyclerActivity : BaseActivity<ActivityRecyclerBinding, RecyclerViewModel>() {

    val adapter:BaseBinderAdapter= BaseBinderAdapter()

    /*override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_recycler //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }*/

    override fun initView(savedInstanceState: Bundle?) {
        listener()
        adapter.addItemBinder(ImageEntity::class.java,ImageItemBinder())
            .addItemBinder(String::class.java,TextItemBinder())

        //LinearLayoutManager已经在布局文件里设置了 所以这里只要设置adapter就可以了
        viewData?.rvRecycler?.adapter=adapter

        adapter.setList(DataUtils.getRecyclerData())

    }

    private fun listener() {
        viewData?.srlRefresh?.setOnRefreshListener {
            adapter.setList(DataUtils.getRecyclerData())
            viewData?.srlRefresh?.isRefreshing=false
        }
    }

    override fun initVariableId(): Int {
        return BR.RecyclerViewModel;//这里是为了绑定ViewModel用的 如果不需要请返回0
    }


}
