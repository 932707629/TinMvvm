package com.soushin.tinmvvm.mvvm.ui

import android.Manifest
import android.os.Bundle
import android.view.View
import com.blankj.ALog
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.base.BaseActivity
import com.soushin.tinmvvm.base.go
import com.soushin.tinmvvm.databinding.ActivityMainBinding
import com.soushin.tinmvvm.mvvm.ui.fragment.CategoryFragment
import com.soushin.tinmvvm.mvvm.viewmodel.MainViewModel
import com.soushin.tinmvvm.utils.FragmentUtils
import com.soushin.tinmvvm.utils.PermissionUtil
import com.tbruyelle.rxpermissions2.RxPermissions


/**
 * 主页
 * https://juejin.im/post/5da90c54f265da5b932e7960  学习的网址
 * @author Soushin
 * @time 2020/1/7 13:39
 */
class MainActivity : BaseActivity<ActivityMainBinding,MainViewModel>() {

//    lateinit var mainViewModel:MainViewModel

    override fun initView(savedInstanceState: Bundle?) :Int{
        return R.layout.activity_main
    }

    //为了保证每次界面销毁重启后，都可以保存之前的值，我们需要在onCreate()中，给控件赋值为 textViewContent
    override fun initData(savedInstanceState: Bundle?) {

        dataBinding?.onClick= View.OnClickListener {
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
                    FragmentUtils.addFragment(supportFragmentManager,CategoryFragment(),R.id.fl_container)
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
                    val rxPermissions=RxPermissions(this)
                    val pms= arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

                    PermissionUtil.requestPermission(object :PermissionUtil.RequestPermission{
                        override fun onRequestPermissionSuccess() {
                            ALog.d("onRequestPermissionSuccess");
                        }

                        override fun onRequestPermissionFailure(permissions: List<String>?) {
                            ALog.d("onRequestPermissionFailure$permissions");
                        }

                        override fun onRequestPermissionFailureWithAskNeverAgain(permissions: List<String>?) {
                            ALog.d("onRequestPermissionFailureWithAskNeverAgain$permissions");
                        }
                    }, rxPermissions,this, pms)
                }
            }
        }
    }


    //BR.xxxxViewModel是kotlin-kapt插件默认生成的 对应xml文件里的xxxxViewModel
    override fun initVariableId(): Int {
        return BR.mainViewModel
    }

    override fun useFragment(): Boolean {
        return true
    }


}
