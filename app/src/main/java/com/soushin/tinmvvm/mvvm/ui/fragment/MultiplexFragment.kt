package com.soushin.tinmvvm.mvvm.ui.fragment

import android.os.Bundle
import androidx.navigation.Navigation
import com.blankj.ALog
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.databinding.FragmentMultiplexBinding
import com.soushin.tinmvvm.mvvm.adapter.MultiplexAdapter
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
    companion object {
        fun newInstance(): MultiplexFragment {
            return MultiplexFragment()
        }
    }
    override fun initView(savedInstanceState: Bundle?) {
        mViewData?.apply {
            ALog.i("开始创建新的", savedInstanceState)
            mViewModel?.loadData()
            ///这里生命周期监听 绝不可用requireActivity() 否则会有内存泄露
            mViewModel?.viewEvent?.observe(this@MultiplexFragment,{
                val multiTypeAdapter = MultiplexAdapter()
                rvRecycler.initLayoutManager()
                rvRecycler.adapter = multiTypeAdapter
                multiTypeAdapter.setList(it)
                multiTypeAdapter.setOnItemClickListener { adapter, view, position ->
                    val navController = Navigation.findNavController(view)
                    if (navController.currentDestination?.id == R.id.multiplexFragment){
                        navController.navigate(R.id.action_multiplexFragment_to_recyclerFragment)
                    }
                }
            })
        }
    }

    override fun initVariableId(): Int {
        return BR.MultiplexViewModel
    }


}
