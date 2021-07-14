package com.soushin.tinmvvm.mvvm.ui.fragment

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.blankj.ALog
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.MyService
import com.soushin.tinmvvm.config.getThis
import com.soushin.tinmvvm.databinding.FragmentHomeBinding
import com.soushin.tinmvvm.mvvm.viewmodel.HomeViewModel
import com.soushin.tinmvvm.utils.PermissionUtil
import com.tbruyelle.rxpermissions2.RxPermissions
import me.soushin.tinmvvm.base.BaseFragment

/**
 *
 * @auther SouShin
 * @time 2021/7/12 14:43
 */
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
    private var serviceIntent: Intent?=null

    //为了保证每次界面销毁重启后，都可以保存之前的值，我们需要在onCreate()中，给控件赋值为 textViewContent
    override fun initView(savedInstanceState: Bundle?) {
        viewModel?.viewEvent?.observe(this,{
            when(it){
                1->{
                   requestPermission()
                }
                2->{
                    //启动自定义service
                    if (serviceIntent==null){
                        serviceIntent= Intent(requireContext(), MyService::class.java)
                        requireActivity().startService(serviceIntent)
                    }else {
                        requireActivity().stopService(Intent(requireContext(), MyService::class.java))
                        this.serviceIntent=null
                    }
                }
            }
        })
    }

    override fun initVariableId(): Int {
        return BR.HomeViewModel;//这里是为了绑定ViewModel用的 如果不需要请返回0
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