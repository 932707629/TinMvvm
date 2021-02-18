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

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_worker
    }

    override fun initData(savedInstanceState: Bundle?) {

        viewData?.onClick = View.OnClickListener {
            when (it.id) {
                R.id.btn_change -> {
                    //PeriodicWorkRequest 任务循环执行
                    //OneTimeWorkRequest 任务执行一次
                    viewModel?.changeStatus(this)
//            val request = OneTimeWorkRequest.Builder(BackGroundWorker::class.java)
//                .setInputData(Data.Builder().putString("key","value").build())
//                .build()
//            val state= WorkManager.getInstance().enqueue(request)

//            WorkManager.getInstance().getWorkInfoByIdLiveData(request.id).observe(this,
//                Observer {
//                    ALog.e("接收任务返回结果",it.outputData.getString("result"))
//                })
                }
            }
        }

    }

    override fun initVariableId(): Int {
        return BR.workerViewModel
    }


}
