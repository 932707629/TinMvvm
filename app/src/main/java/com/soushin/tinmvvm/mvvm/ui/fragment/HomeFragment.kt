package com.soushin.tinmvvm.mvvm.ui.fragment

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.navigation.*
import androidx.navigation.ui.NavigationUI
import com.blankj.ALog
import com.jeremyliao.liveeventbus.LiveEventBus

import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.MyService
import com.soushin.tinmvvm.config.go
import me.soushin.tinmvvm.base.BaseFragment
import com.soushin.tinmvvm.databinding.FragmentHomeBinding
import com.soushin.tinmvvm.mvvm.ui.*
import com.soushin.tinmvvm.mvvm.viewmodel.HomeViewModel
import com.soushin.tinmvvm.utils.FragmentUtils
import com.soushin.tinmvvm.utils.PermissionUtil
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    private var serviceIntent: Intent?=null

    //为了保证每次界面销毁重启后，都可以保存之前的值，我们需要在onCreate()中，给控件赋值为 textViewContent
    override fun initView(savedInstanceState: Bundle?) {
        viewData?.onClick= View.OnClickListener {
            ALog.e("点击切换数据了")
            when(it.id){
                R.id.btn_change->{
                    viewModel?.getDatas()
                }
                R.id.btn_return->{
                    viewModel?.tvContent?.value="Hello World"
                }
                R.id.btn_worker->{
                    go(WorkerActivity::class.java)
                }
                R.id.btn_create_fragment->{
//                    FragmentUtils.add(childFragmentManager, CategoryFragment(),R.id.fl_container)
                    Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_categoryFragment)
                }
                R.id.btn_multiplex->{
                    go(MultiplexActivity::class.java)
                }
                R.id.btn_recycler->{
                    go(RecyclerActivity::class.java)
                }
                R.id.btn_coroutine->{
                    go(CoroutineActivity::class.java)
                }
                R.id.btn_permission->{
                    val rxPermissions= RxPermissions(this)
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
                R.id.btn_thread_pool->{
                    go(ThreadPoolActivity::class.java)
                }
                R.id.btn_crash->{
                    throw RuntimeException("模拟java运行时异常")
                }
                R.id.btn_service->{
                    //启动自定义service
                    if (serviceIntent==null){
                        serviceIntent= Intent(requireContext(), MyService::class.java)
                        requireActivity().startService(serviceIntent)
                    }else {
                        requireActivity().stopService(Intent(requireContext(), MyService::class.java))
                        this.serviceIntent=null
                    }
                }
                R.id.btn_file_write->{
                    GlobalScope.launch {
                        withContext(Dispatchers.IO){
                            ALog.i("getExternalStorageDirectory", Environment.getExternalStorageDirectory().absolutePath)
                            ALog.i("getExternalStoragePublicDirectory",
                                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath)
                            ALog.i("getDataDirectory", Environment.getDataDirectory().absolutePath)
                            ALog.i("getDownloadCacheDirectory", Environment.getDownloadCacheDirectory().absolutePath)
                            ALog.i("getRootDirectory", Environment.getRootDirectory().absolutePath)
                            ALog.i("DIRECTORY_MUSIC",requireActivity().getExternalFilesDir(Environment.DIRECTORY_MUSIC)?.absolutePath)
                            ALog.i("DIRECTORY_ALARMS",requireActivity().getExternalFilesDir(Environment.DIRECTORY_ALARMS)?.absolutePath)
                            ALog.i("DIRECTORY_DCIM",requireActivity().getExternalFilesDir(Environment.DIRECTORY_DCIM)?.absolutePath)
                            ALog.i("DIRECTORY_DOCUMENTS",requireActivity().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath)
                            ALog.i("DIRECTORY_DOWNLOADS",requireActivity().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.absolutePath)
                            ALog.i("DIRECTORY_MOVIES",requireActivity().getExternalFilesDir(Environment.DIRECTORY_MOVIES)?.absolutePath)
                            ALog.i("DIRECTORY_NOTIFICATIONS",requireActivity().getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS)?.absolutePath)
                            ALog.i("DIRECTORY_PICTURES",requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.absolutePath)
                            ALog.i("DIRECTORY_PODCASTS",requireActivity().getExternalFilesDir(Environment.DIRECTORY_PODCASTS)?.absolutePath)
                            ALog.i("DIRECTORY_RINGTONES",requireActivity().getExternalFilesDir(Environment.DIRECTORY_RINGTONES)?.absolutePath)
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                                ALog.i("DIRECTORY_SCREENSHOTS",requireActivity().getExternalFilesDir(Environment.DIRECTORY_SCREENSHOTS)?.absolutePath)
                                ALog.i("DIRECTORY_AUDIOBOOKS",requireActivity().getExternalFilesDir(Environment.DIRECTORY_AUDIOBOOKS)?.absolutePath)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun initVariableId(): Int {
        return BR.HomeViewModel;//这里是为了绑定ViewModel用的 如果不需要请返回0
    }
}