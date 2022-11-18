package com.soushin.tinmvvm.mvvm.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.withCreated
import androidx.lifecycle.withResumed
import androidx.lifecycle.withStarted
import com.blankj.ALog
import com.soushin.tinmvvm.BR
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.databinding.FragmentCoroutineBinding
import com.soushin.tinmvvm.mvvm.viewmodel.CoroutineViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.soushin.tinmvvm.base.DataBindingFragment
import me.soushin.tinmvvm.config.DataBindingConfig

/**
 *
 * 协程和LiveData、Flow相关的一些用法
 * @see(https://www.jianshu.com/p/16aa5eaa60d7)
 * 协程本身可以理解为一个对线程的封装库，跟我们用的线程池异曲同工
 * 启动10w个协程跟启动10w个线程是不能相提并论的  但如果是在线程池里两相比较差别不大
 * 所以说协程和线程池相比性能上没有比较明显的优势
 * @auther SouShin
 * @time 2020/6/24 10:29
 */
class CoroutineFragment : DataBindingFragment<FragmentCoroutineBinding, CoroutineViewModel>() {

    //配置当前页面的内容 各项参数都可为空
    //BR.xxxxViewModel是kotlin-kapt插件默认生成的 对应xml文件里的xxxxViewModel
    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(layoutId = R.layout.fragment_coroutine,variableId = BR.CoroutineViewModel,
            vmClass = CoroutineViewModel::class.java)
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launch(Dispatchers.IO) {
            //AppCompatActivity 或 Fragment，则可以使用 lifecycleScope，当 lifeCycle 被销毁时，操作也会被取消。
            withCreated {
                ALog.i("launchWithCreated",Thread.currentThread().name);
            }
            withStarted {
                ALog.i("launchWithStarted",Thread.currentThread().name);
            }
            withResumed {
                ALog.i("launchWithResumed",Thread.currentThread().name);
            }
        }
        lifecycleScope.launchWhenCreated {
            withContext(Dispatchers.IO){
                ALog.i("launchWhenCreated",Thread.currentThread().name);
            }
            //有些时候，您可能还需要在生命周期的某个状态 (启动时/恢复时等)
        // 执行一些操作，这时您可以使用 launchWhenStarted、launchWhenResumed、launchWhenCreated 这些方法
        }
        lifecycleScope.launchWhenStarted {
            withContext(Dispatchers.IO){
                ALog.i("launchWhenStarted",Thread.currentThread().name);
            }
            //有些时候，您可能还需要在生命周期的某个状态 (启动时/恢复时等)
            // 执行一些操作，这时您可以使用 launchWhenStarted、launchWhenResumed、launchWhenCreated 这些方法
        }
        lifecycleScope.launchWhenResumed {
            withContext(Dispatchers.IO){
                ALog.i("launchWhenResumed",Thread.currentThread().name);
            }
            //有些时候，您可能还需要在生命周期的某个状态 (启动时/恢复时等)
            // 执行一些操作，这时您可以使用 launchWhenStarted、launchWhenResumed、launchWhenCreated 这些方法
        }
        //最后一种作用域的情况是贯穿整个应用。如果这个操作非常重要，
    // 您需要确保它一定被执行，这时请考虑使用 WorkManager。
    // 比如您编写了一个发推的应用，希望撰写的推文被发送到服务器上，
    // 那这个操作就需要使用 WorkManager 来确保执行。而
    // 如果您的操作只是清理一下本地存储，那可以考虑使用 Application Scope，
    // 因为这个操作的重要性不是很高，完全可以等到下次应用启动时再做。

        mViewModel?.apply {
            result.observe(viewLifecycleOwner) {
                ALog.i("result的观察${it}");
            }
            result1.observe(viewLifecycleOwner) {
                ALog.i("result1的观察${it}");
            }
            result2.observe(viewLifecycleOwner) {
                ALog.i("result1的更新结果${it}");
            }
        }

    }

}