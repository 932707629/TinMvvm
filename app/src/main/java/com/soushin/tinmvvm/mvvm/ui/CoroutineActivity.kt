package com.soushin.tinmvvm.mvvm.ui

import android.os.Bundle
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.databinding.ActivityCoroutineBinding
import com.soushin.tinmvvm.mvvm.viewmodel.CoroutineViewModel
import me.soushin.tinmvvm.base.BaseActivity

/**
 * 协程本身可以理解为一个对线程的封装库，跟我们用的线程池异曲同工
 * 启动10w个协程跟启动10w个线程是不能相提并论的  但如果是在线程池里两相比较差别不大
 * 所以说协程和线程池相比性能上没有比较明显的优势
 * @auther SouShin
 * @time 2020/6/24 10:29
 */
class CoroutineActivity : BaseActivity<ActivityCoroutineBinding, CoroutineViewModel>() {

    /*override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_coroutine //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }*/

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initVariableId(): Int {
        return BR.CoroutineViewModel;//这里是为了绑定ViewModel用的 如果不需要请返回0
    }


}
