package com.soushin.tinmvvm.mvvm.ui

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.base.BaseActivity
import com.soushin.tinmvvm.base.BaseAdapter
import com.soushin.tinmvvm.databinding.ActivityRecyclerBinding
import com.soushin.tinmvvm.mvvm.adapter.MultiTypeAdapter
import com.soushin.tinmvvm.mvvm.adapter.RecyclerAdapter
import com.soushin.tinmvvm.mvvm.model.entity.AuthorEntity
import com.soushin.tinmvvm.mvvm.model.entity.CategoryBean
import com.soushin.tinmvvm.mvvm.viewmodel.RecyclerViewModel
import com.soushin.tinmvvm.widget.ParentRecyclerView
import java.util.ArrayList

/**
 * ================================================
 * Description:
 * <p>
 * Created by TinMvvmTemplate on 01/20/2020 15:26
 * ================================================
 */

class RecyclerActivity : BaseActivity<ActivityRecyclerBinding, RecyclerViewModel>() {

    private val mDataList = ArrayList<AuthorEntity>()

    private val strArray = arrayOf("关注", "推荐", "视频", "直播", "图片", "段子", "精华", "热门")

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_recycler //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    override fun initData(savedInstanceState: Bundle?) {
        val rvRecycler=findViewById<ParentRecyclerView>(R.id.rvRecycler)
        rvRecycler.initLayoutManager()
        val multiTypeAdapter = RecyclerAdapter()
        for (i in 0..8) {
            mDataList.add(AuthorEntity(BaseAdapter.ITEM_ONE,2, arrayListOf(),""))
        }
        val childDatas= arrayListOf<String>()
        strArray.forEach {
            childDatas.add(it)
        }
        mDataList.add(AuthorEntity(BaseAdapter.ITEM_TWO,2, childDatas,""))
        rvRecycler.adapter = multiTypeAdapter
        multiTypeAdapter.setNewData(mDataList)
    }

    override fun initVariableId(): Int {
        return BR.RecyclerViewModel
    }


}
