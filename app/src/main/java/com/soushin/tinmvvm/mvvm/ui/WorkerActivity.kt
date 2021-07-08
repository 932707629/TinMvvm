package com.soushin.tinmvvm.mvvm.ui

import android.os.Bundle
import android.view.View
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.databinding.ActivityWorkerBinding
import com.soushin.tinmvvm.mvvm.viewmodel.WorkerViewModel
import me.soushin.tinmvvm.base.BaseActivity

/**
 *
 * @author Soushin
 * @time 2020/1/10 15:40
 */

class WorkerActivity : BaseActivity<ActivityWorkerBinding, WorkerViewModel>() {

    /*override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_worker
    }*/

    override fun initView(savedInstanceState: Bundle?) {

        viewData?.onClick = View.OnClickListener {
            when (it.id) {
                R.id.btn_change -> {
                    //PeriodicWorkRequest 任务循环执行
                    //OneTimeWorkRequest 任务执行一次
                    viewModel?.changeStatus(this)

                }
            }
        }

    }

    override fun initVariableId(): Int {
        return BR.workerViewModel
    }


}
