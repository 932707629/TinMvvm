package com.soushin.tinmvvm.mvvm.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.base.BaseActivity
import com.soushin.tinmvvm.databinding.ActivityRecyclerBinding
import com.soushin.tinmvvm.mvvm.viewmodel.RecyclerViewModel
import kotlinx.android.synthetic.main.activity_recycler.*

/**
 * ================================================
 * Description:
 * <p>
 * Created by TinMvvmTemplate on 03/02/2020 16:36
 * ================================================
 */

class RecyclerActivity : BaseActivity<ActivityRecyclerBinding, RecyclerViewModel>() {


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_recycler //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    override fun initData(savedInstanceState: Bundle?) {
        viewModel?.load()
        srl_refresh.setOnRefreshListener {
            viewModel?.load()
        }

        viewModel?.refresh?.observe(this, Observer {
            srl_refresh.isRefreshing=it//改变刷新状态
        })
    }

    override fun initVariableId(): Int {
        return BR.RecyclerViewModel;//这里是为了绑定ViewModel用的 如果不需要请返回0
    }


}
