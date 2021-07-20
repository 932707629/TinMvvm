package com.soushin.tinmvvm.mvvm.ui.fragment

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.blankj.ALog
import com.chad.library.adapter.base.BaseBinderAdapter
import com.google.android.flexbox.*
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.app.getThis
import com.soushin.tinmvvm.app.service.MyService
import com.soushin.tinmvvm.app.utils.PermissionUtil
import com.soushin.tinmvvm.databinding.FragmentHomeBinding
import com.soushin.tinmvvm.mvvm.adapter.itembinder.TabComponentItemBinder
import com.soushin.tinmvvm.mvvm.viewmodel.HomeViewModel
import com.tbruyelle.rxpermissions3.RxPermissions
import me.soushin.tinmvvm.base.DataBindingFragment
import me.soushin.tinmvvm.config.DataBindingConfig

/**
 *
 * @auther SouShin
 * @time 2021/7/12 14:43
 */
class HomeFragment : DataBindingFragment<FragmentHomeBinding, HomeViewModel>() {
    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
    private var serviceIntent: Intent?=null

    //为了保证每次界面销毁重启后，都可以保存之前的值，我们需要在onCreate()中，给控件赋值为 textViewContent
    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) {
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
            rvHome.layoutManager = layoutManager
            val adapter = BaseBinderAdapter()
            adapter.addItemBinder(TabComponentItemBinder())
            adapter.setOnItemClickListener { ada, view, position ->
                mViewModel?.onItemClick(ada.getItem(position) as String,view,position)
            }
            rvHome.adapter=adapter
            mViewModel?.loadData()
            mViewModel?.viewEvent?.observe(getThis(),{
                when{
                    it.key==0 && (it.value is MutableList<*>)->{
                        adapter.setList(it.value as MutableList<String>)
                    }
                    it.key==1 ->{

//                        requestPermission()
                    }
                    it.key==2 ->{
                        //启动自定义service
                        if (serviceIntent==null){
                            serviceIntent= Intent(requireContext(), MyService::class.java)
                            requireActivity().startService(serviceIntent)
                        }else {
                            requireActivity().stopService(Intent(requireContext(), MyService::class.java))
                            serviceIntent=null
                        }
                    }
                }
            })
        }

    }

    //配置当前页面的内容 各项参数都可为空
    //BR.xxxxViewModel是kotlin-kapt插件默认生成的 对应xml文件里的xxxxViewModel
    override fun getDataBindingConfig(): DataBindingConfig? {
        //演示addBindingParam()的用法
        return DataBindingConfig(layoutId = R.layout.fragment_home,
            variableId = BR.HomeViewModel,vmClass = HomeViewModel::class.java)
    }

    private fun requestPermission(){
        val rxPermissions= RxPermissions(getThis())
        val pms= arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        PermissionUtil.requestPermission(
            permissions = pms,
            lifecycle = this,
            rxPermissions = rxPermissions,
            requestPermission = object : PermissionUtil.RequestPermission{
                override fun onRequestPermissionSuccess() {
                    ALog.d("onRequestPermissionSuccess");
                }
                override fun onRequestPermissionFailure(permissions: List<String>?) {
                    ALog.d("onRequestPermissionFailure$permissions");
                }
                override fun onRequestPermissionFailureWithAskNeverAgain(permissions: List<String>?) {
                    ALog.d("onRequestPermissionFailureWithAskNeverAgain$permissions");
                }
            })
    }


}