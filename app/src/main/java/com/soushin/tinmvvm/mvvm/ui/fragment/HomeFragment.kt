package com.soushin.tinmvvm.mvvm.ui.fragment

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.blankj.ALog
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.app.getThis
import com.soushin.tinmvvm.app.service.MyService
import com.soushin.tinmvvm.databinding.FragmentHomeBinding
import com.soushin.tinmvvm.mvvm.viewmodel.HomeViewModel
import com.soushin.tinmvvm.app.utils.PermissionUtil
import com.soushin.tinmvvm.mvvm.ui.nav
import com.soushin.tinmvvm.mvvm.viewmodel.CoroutineViewModel
import com.tbruyelle.rxpermissions3.RxPermissions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.soushin.tinmvvm.base.BaseApp
import me.soushin.tinmvvm.base.DataBindingFragment
import me.soushin.tinmvvm.config.DataBindingConfig
import me.soushin.tinmvvm.utils.AppManager
import me.soushin.tinmvvm.utils.throttleClick

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
        mViewModel?.viewEvent?.observe(this,{
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

    //配置当前页面的内容 各项参数都可为空
    //BR.xxxxViewModel是kotlin-kapt插件默认生成的 对应xml文件里的xxxxViewModel
    override fun getDataBindingConfig(): DataBindingConfig? {
        //演示addBindingParam()的用法
        return DataBindingConfig(layoutId = R.layout.fragment_home,
            variableId = BR.HomeViewModel,vmClass = HomeViewModel::class.java).addBindingParam(BR.viewClick,ClickProxy())
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

    inner class ClickProxy{
        fun onViewClick():View.OnClickListener{
            ///防止连续点击 当快速点击的时候Navigation会可能会出
            //java.lang.IllegalArgumentException: Navigation action/destination com.soushin.tinmvvm:id/action_homeFragment_to_recyclerFragment cannot be found from the current destination Destination(com.soushin.tinmvvm:id/categoryFragment) label=CategoryFragment class=com.soushin.tinmvvm.mvvm.ui.fragment.CategoryFragment
            //这是因为当前的destination已经不是HomeFragment 而是已经换成了categoryFragment 所以这时候再去action_homeFragment_to_recyclerFragment必然会抛异常
            //所以跳转之前最好判断当前页面是否是HomeFragment
            //这在页面跳转时添加转场动画会显得非常明显(转场动画有duration)
            return throttleClick {
                ALog.e("点击切换数据了",nav().currentDestination?.label)
                if (nav().currentDestination?.id != R.id.homeFragment) return@throttleClick
                when(it.id){
                    R.id.btn_change->{
                        mViewModel?.getData()
                    }
                    R.id.btn_return->{
                        mViewModel?.tvContent?.value="Hello World"
                    }
                    R.id.btn_worker->{
                        Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_workerFragment)
                    }
                    R.id.btn_create_fragment->{
//                    FragmentUtils.add(childFragmentManager, CategoryFragment(),R.id.fl_container)
                        Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_categoryFragment)
                    }
                    R.id.btn_multiplex->{
                        Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_multiplexFragment)
                    }
                    R.id.btn_recycler->{
                        Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_recyclerFragment)
                    }
                    R.id.btn_coroutine->{
                        Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_coroutineFragment)
                    }
                    R.id.btn_tab_viewpager->{
                        Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_tablayoutViewpager2Fragment)
//                    Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_bottomNavigationFragment)
                    }
                    R.id.btn_permission->{
                        mViewModel?.viewEvent?.value = 1
                    }
                    R.id.btn_thread_pool->{
                        Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_threadPoolFragment)
                    }
                    R.id.btn_crash->{
                        throw RuntimeException("模拟java运行时异常")
                    }
                    R.id.btn_service->{
                        mViewModel?.viewEvent?.value = 2
                    }
                    R.id.btn_file_write->{
                        ALog.i("getExternalStorageDirectory", Environment.getExternalStorageDirectory().absolutePath)
                        ALog.i("getExternalStoragePublicDirectory",
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath)
                        ALog.i("getDataDirectory", Environment.getDataDirectory().absolutePath)
                        ALog.i("getDownloadCacheDirectory", Environment.getDownloadCacheDirectory().absolutePath)
                        ALog.i("getRootDirectory", Environment.getRootDirectory().absolutePath)
                        val activity = AppManager.get().currentActivity
                        ALog.i("DIRECTORY_MUSIC",activity?.getExternalFilesDir(Environment.DIRECTORY_MUSIC)?.absolutePath)
                        ALog.i("DIRECTORY_ALARMS",activity?.getExternalFilesDir(
                            Environment.DIRECTORY_ALARMS)?.absolutePath)
                        ALog.i("DIRECTORY_DCIM",activity?.getExternalFilesDir(
                            Environment.DIRECTORY_DCIM)?.absolutePath)
                        ALog.i("DIRECTORY_DOCUMENTS",activity?.getExternalFilesDir(
                            Environment.DIRECTORY_DOCUMENTS)?.absolutePath)
                        ALog.i("DIRECTORY_DOWNLOADS",activity?.getExternalFilesDir(
                            Environment.DIRECTORY_DOWNLOADS)?.absolutePath)
                        ALog.i("DIRECTORY_MOVIES",activity?.getExternalFilesDir(
                            Environment.DIRECTORY_MOVIES)?.absolutePath)
                        ALog.i("DIRECTORY_NOTIFICATIONS",activity?.getExternalFilesDir(
                            Environment.DIRECTORY_NOTIFICATIONS)?.absolutePath)
                        ALog.i("DIRECTORY_PICTURES",activity?.getExternalFilesDir(
                            Environment.DIRECTORY_PICTURES)?.absolutePath)
                        ALog.i("DIRECTORY_PODCASTS",activity?.getExternalFilesDir(
                            Environment.DIRECTORY_PODCASTS)?.absolutePath)
                        ALog.i("DIRECTORY_RINGTONES",activity?.getExternalFilesDir(
                            Environment.DIRECTORY_RINGTONES)?.absolutePath)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                            ALog.i("DIRECTORY_SCREENSHOTS",activity?.getExternalFilesDir(
                                Environment.DIRECTORY_SCREENSHOTS)?.absolutePath)
                            ALog.i("DIRECTORY_AUDIOBOOKS",activity?.getExternalFilesDir(
                                Environment.DIRECTORY_AUDIOBOOKS)?.absolutePath)
                        }
                    }
                }
            }
        }

    }


}