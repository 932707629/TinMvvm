package com.soushin.tinmvvm.mvvm.ui

import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager

import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.base.BaseActivity
import com.soushin.tinmvvm.base.getThis
import com.soushin.tinmvvm.databinding.ActivityRecyclerBinding
import com.soushin.tinmvvm.mvvm.adapter.RecyclerAdapter
import com.soushin.tinmvvm.mvvm.viewmodel.RecyclerViewModel
import com.soushin.tinmvvm.utils.DataUtils
import kotlinx.android.synthetic.main.activity_recycler.*

/**
 * ================================================
 * Description:
 * <p>
 * Created by TinMvvmTemplate on 01/20/2020 15:26
 * ================================================
 */

class RecyclerActivity : BaseActivity<ActivityRecyclerBinding, RecyclerViewModel>() {

    var recyclerAdapter:RecyclerAdapter?=null

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_recycler //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    override fun initData(savedInstanceState: Bundle?) {
        rvRecycler.layoutManager=LinearLayoutManager(getThis())
        recyclerAdapter=RecyclerAdapter()
        rvRecycler.adapter=recyclerAdapter
        recyclerAdapter?.setNewData(DataUtils.getRecyclerDatas())

    }




    override fun initVariableId(): Int {
        return BR.RecyclerViewModel
    }


}
