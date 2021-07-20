package com.soushin.tinmvvm.mvvm.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.databinding.FragmentWorkerBinding
import com.soushin.tinmvvm.mvvm.viewmodel.WorkerViewModel
import me.soushin.tinmvvm.base.DataBindingFragment
import me.soushin.tinmvvm.config.DataBindingConfig

/**
 *
 * @author Soushin
 * @time 2020/1/10 15:40
 */

class WorkerFragment : DataBindingFragment<FragmentWorkerBinding, WorkerViewModel>() {

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) {
        mViewData?.onClick = View.OnClickListener {
            when (it.id) {
                R.id.btn_change -> {
                    //PeriodicWorkRequest 任务循环执行
                    //OneTimeWorkRequest 任务执行一次
                    mViewModel?.changeStatus()
                }
            }
        }
    }

    //配置当前页面的内容 各项参数都可为空
    //BR.xxxxViewModel是kotlin-kapt插件默认生成的 对应xml文件里的xxxxViewModel
    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(layoutId = R.layout.fragment_worker,variableId = BR.workerViewModel,
            vmClass = WorkerViewModel::class.java)
    }

}
