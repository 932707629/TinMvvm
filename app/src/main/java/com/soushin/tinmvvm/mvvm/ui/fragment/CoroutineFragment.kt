package com.soushin.tinmvvm.mvvm.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.databinding.FragmentCoroutineBinding
import com.soushin.tinmvvm.mvvm.viewmodel.CategoryViewModel
import com.soushin.tinmvvm.mvvm.viewmodel.CoroutineViewModel
import me.soushin.tinmvvm.base.DataBindingFragment
import me.soushin.tinmvvm.config.DataBindingConfig

/**
 * 协程本身可以理解为一个对线程的封装库，跟我们用的线程池异曲同工
 * 启动10w个协程跟启动10w个线程是不能相提并论的  但如果是在线程池里两相比较差别不大
 * 所以说协程和线程池相比性能上没有比较明显的优势
 * @auther SouShin
 * @time 2020/6/24 10:29
 */
class CoroutineFragment : DataBindingFragment<FragmentCoroutineBinding, CoroutineViewModel>() {

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) {

    }

    //配置当前页面的内容 各项参数都可为空
    //BR.xxxxViewModel是kotlin-kapt插件默认生成的 对应xml文件里的xxxxViewModel
    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(layoutId = R.layout.fragment_coroutine,variableId = BR.CoroutineViewModel,
            vmClass = CoroutineViewModel::class.java)
    }

}