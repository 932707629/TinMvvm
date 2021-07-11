package com.soushin.tinmvvm.mvvm.ui.fragment

import android.os.Bundle
import androidx.navigation.Navigation
import com.blankj.ALog
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.databinding.FragmentMultiplexBinding
import com.soushin.tinmvvm.mvvm.adapter.BaseAdapter
import com.soushin.tinmvvm.mvvm.adapter.MultiplexAdapter
import com.soushin.tinmvvm.mvvm.model.entity.AuthorEntity
import com.soushin.tinmvvm.mvvm.viewmodel.MultiplexViewModel
import me.soushin.tinmvvm.base.BaseFragment
import java.util.*

/**
 * ================================================
 * Description:复杂布局
 * <p>
 * Created by TinMvvmTemplate on 01/20/2020 15:26
 * ================================================
 */

class MultiplexFragment : BaseFragment<FragmentMultiplexBinding, MultiplexViewModel>() {

    private val mDataList = ArrayList<AuthorEntity>()

    private val strArray = arrayOf("关注", "推荐", "视频", "直播", "图片", "段子", "精华", "热门")

    override fun initView(savedInstanceState: Bundle?) {
        viewData?.apply {
            ALog.i("开始创建新的",savedInstanceState)
            rvRecycler.initLayoutManager()
            val multiTypeAdapter = MultiplexAdapter()
            for (i in 0..8) {
                mDataList.add(AuthorEntity(BaseAdapter.ITEM_ONE,2, arrayListOf(),""))
            }
            val childDatas= arrayListOf<String>()
            strArray.forEach {
                childDatas.add(it)
            }
            mDataList.add(AuthorEntity(BaseAdapter.ITEM_TWO,2, childDatas,""))
            rvRecycler.adapter = multiTypeAdapter
            multiTypeAdapter.setNewInstance(mDataList)
            multiTypeAdapter.setOnItemClickListener { adapter, view, position ->
                Navigation.findNavController(view)
                    .navigate(R.id.action_multiplexFragment_to_recyclerFragment)
            }
        }
    }

    override fun initVariableId(): Int {
        return BR.MultiplexViewModel
    }


}
