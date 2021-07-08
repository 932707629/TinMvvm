package com.soushin.tinmvvm.mvvm.ui

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle;
import android.os.Environment
import android.view.View
import com.blankj.ALog
import com.jeremyliao.liveeventbus.LiveEventBus
import me.soushin.tinmvvm.base.BaseActivity;
import com.soushin.tinmvvm.databinding.ActivityTestBinding;
import com.soushin.tinmvvm.mvvm.viewmodel.TestViewModel;
import com.soushin.tinmvvm.R;
import com.soushin.tinmvvm.BR;
import com.soushin.tinmvvm.MyService
import com.soushin.tinmvvm.config.go
import com.soushin.tinmvvm.mvvm.ui.fragment.CategoryFragment
import com.soushin.tinmvvm.utils.FragmentUtils
import com.soushin.tinmvvm.utils.PermissionUtil
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * 主页
 * https://juejin.im/post/5da90c54f265da5b932e7960  学习的网址
 * @author Soushin
 * @time 2020/1/7 13:39
 */
class TestActivity : BaseActivity<ActivityTestBinding, TestViewModel>() {

    override fun initVariableId(): Int {
        return BR.TestViewModel;//这里是为了绑定ViewModel用的 如果不需要请返回0
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
                    FragmentUtils.add(supportFragmentManager, CategoryFragment(),R.id.fl_container)
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
                        serviceIntent= Intent(this, MyService::class.java)
                        startService(serviceIntent)
                    }else {
                        stopService(Intent(this, MyService::class.java))
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
                            ALog.i("DIRECTORY_MUSIC",getExternalFilesDir(Environment.DIRECTORY_MUSIC)?.absolutePath)
                            ALog.i("DIRECTORY_ALARMS",getExternalFilesDir(Environment.DIRECTORY_ALARMS)?.absolutePath)
                            ALog.i("DIRECTORY_DCIM",getExternalFilesDir(Environment.DIRECTORY_DCIM)?.absolutePath)
                            ALog.i("DIRECTORY_DOCUMENTS",getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath)
                            ALog.i("DIRECTORY_DOWNLOADS",getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.absolutePath)
                            ALog.i("DIRECTORY_MOVIES",getExternalFilesDir(Environment.DIRECTORY_MOVIES)?.absolutePath)
                            ALog.i("DIRECTORY_NOTIFICATIONS",getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS)?.absolutePath)
                            ALog.i("DIRECTORY_PICTURES",getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.absolutePath)
                            ALog.i("DIRECTORY_PODCASTS",getExternalFilesDir(Environment.DIRECTORY_PODCASTS)?.absolutePath)
                            ALog.i("DIRECTORY_RINGTONES",getExternalFilesDir(Environment.DIRECTORY_RINGTONES)?.absolutePath)
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                                ALog.i("DIRECTORY_SCREENSHOTS",getExternalFilesDir(Environment.DIRECTORY_SCREENSHOTS)?.absolutePath)
                                ALog.i("DIRECTORY_AUDIOBOOKS",getExternalFilesDir(Environment.DIRECTORY_AUDIOBOOKS)?.absolutePath)
                            }
                        }
                    }
                }
            }
        }

        LiveEventBus.get("pageChange",Int::class.java)
            .observeSticky(this, {
                if (it==1){
                    FragmentUtils.add(supportFragmentManager, CategoryFragment(),R.id.fl_container)
                }
            })
    }

    override fun useFragment(): Boolean {
        return true
    }

}