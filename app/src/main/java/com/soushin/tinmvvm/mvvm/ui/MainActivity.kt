package com.soushin.tinmvvm.mvvm.ui

import android.os.Bundle
import android.view.View
import com.blankj.ALog
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.base.BaseActivity
import com.soushin.tinmvvm.databinding.ActivityMainBinding
import com.soushin.tinmvvm.mvvm.viewmodel.MainViewModel

/**
 * 主页
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

//        dataBinding?.mainViewModel=viewModel
        dataBinding?.onClick= View.OnClickListener {
            ALog.e("点击切换数据了")
            when(it.id){
                R.id.btn_change->{
                    viewModel?.getDatas()
                }
                R.id.btn_return->{
                    viewModel?.tvContent?.value="Hello World"
                }
            }
        }
    }

    //BR.xxxxViewModel是kotlin-kapt插件默认生成的 对应xml文件里的xxxxViewModel
    override fun initVariableId(): Int {
        return BR.mainViewModel
    }


}
