package com.soushin.tinmvvm.mvvm.ui.fragment

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.ALog
import com.chad.library.adapter.base.BaseBinderAdapter
import com.google.android.flexbox.*
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.app.AppData
import com.soushin.tinmvvm.app.getThis
import com.soushin.tinmvvm.app.service.MyService
import com.soushin.tinmvvm.app.utils.PermissionUtil
import com.soushin.tinmvvm.databinding.FragmentHomeBinding
import com.soushin.tinmvvm.mvvm.adapter.itembinder.TabComponentItemBinder
import com.soushin.tinmvvm.mvvm.viewmodel.HomeViewModel
import com.soushin.tinmvvm.mvvm.viewmodel.WorkerViewModel
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

    //配置当前页面的内容 各项参数都可为空
    //BR.xxxxViewModel是kotlin-kapt插件默认生成的 对应xml文件里的xxxxViewModel
    override fun getDataBindingConfig(): DataBindingConfig? {
        //演示addBindingParam()的用法
        return DataBindingConfig(layoutId = R.layout.fragment_home,
            variableId = BR.HomeViewModel,vmClass = HomeViewModel::class.java,dataBindingComponent = AppData.get().queryComponent())
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        this.adapter=null
    }

    private var serviceIntent: Intent?=null
//    private var adapter:BaseBinderAdapter? = null

    //为了保证每次界面销毁重启后，都可以保存之前的值，我们需要在onCreate()中，给控件赋值为 textViewContent
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
            rvHome.layoutManager = layoutManager
            val adapter = BaseBinderAdapter()
            adapter.addItemBinder(TabComponentItemBinder())
            adapter.setOnItemClickListener { ada, view, position ->
                mViewModel?.onItemClick(ada.getItem(position) as String,view,position)//,fragment.navController
            }
            rvHome.adapter=adapter

            mViewModel?.apply {
                labels.observe(viewLifecycleOwner,{
                    adapter.setList(it)
                })
                viewEvent.observe(viewLifecycleOwner,{
                    when{
                        it.key==0 && (it.value is MutableList<*>)->{
                        }
                        it.key==1 ->{
                            requestPermission()
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