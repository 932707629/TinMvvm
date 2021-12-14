package me.soushin.tinmvvm.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.cancel
import me.soushin.tinmvvm.config.AppComponent
import me.soushin.tinmvvm.config.DataBindingConfig
import me.soushin.tinmvvm.custom.SharedViewModelStore

/**
 * activity基类
 * 可以添加一些常用的基础方法 或者实现方法
 * 不建议这里做太多逻辑操作
 * @author created by Soushin
 * @time 2020/1/7 13 29
 */
abstract class DataBindingActivity<VD : ViewDataBinding,
        VM : BaseViewModel<out BaseRepository>> :AppCompatActivity(){

    protected var mViewModel:VM? = null
    protected var mViewData:VD? = null
    //通过ViewModelProvider可以获取同一个Activity下共享的ViewModel
    protected var mViewModelProvider:ViewModelProvider?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataViewBinding()
        initView(savedInstanceState)
    }

    override fun onDestroy() {
        mViewData?.unbind()
        mViewData=null
        lifecycleScope.cancel()//取消协程事件
        super.onDestroy()
    }

    /**
     * DataBindingUtil.bind和DataBindingUtil.setContentView两种方法有毫秒级的差距 bind+setContentView更快
     * 用DataBindingUtil.bind需要用反射 用DataBindingUtil.setContentView需要上层指定一个layoutId
     */
    open fun dataViewBinding() {
        val activity = this
        getDataBindingConfig()?.apply {
            //如果当前页面的layoutId为空就不会去调用setContentView()实例化mViewData
            layoutId?.let {
                mViewData = if (dataBindingComponent!=null && dataBindingComponent is DataBindingComponent){
                    DataBindingUtil.setContentView(activity, it,dataBindingComponent as DataBindingComponent)
                }else {
                    DataBindingUtil.setContentView(activity, it)
                }
                mViewData!!.lifecycleOwner = this@DataBindingActivity
                bindingParams.forEach {entry->
                    mViewData!!.setVariable(entry.key,entry.value)
                }
            }
            //如果当前页面的vmClass为空就不会去实例化mViewModel
            vmClass?.let {
                mViewModelProvider = if (sharedViewModel()) ViewModelProvider(AppComponent.sharedViewModelStore,defaultViewModelProviderFactory) else ViewModelProvider(activity)
                mViewModel = mViewModelProvider!![it] as VM
                lifecycle.addObserver(mViewModel!!)
                //如果当前页面设置的variableId为空就不会去绑定ViewModel
                variableId?.let {vid->
                    mViewData?.setVariable(vid,mViewModel)
                }
                //为防止直接在DataBindingActivity.initView()调用时出现为空的情况
                if (mViewModel!!.lifecycle == null){
                    mViewModel!!.lifecycle = this@DataBindingActivity
                }
            }
        }
    }

    abstract fun initView(savedInstanceState: Bundle?)

    abstract fun getDataBindingConfig():DataBindingConfig?

    /**
     * 开启对fragment的生命周期监听
     * 可以在这里做一些baseFragment的操作
     */
    open fun useFragment():Boolean{
        return true
    }

    /**
     * 开启ViewModel共享数据
     * true mViewModel实例将于其他Activity、Fragment共享（包括VM中创建的实例）
     * false mViewModel实例不会与其他页面共享
     */
    open fun sharedViewModel():Boolean{
        return true
    }



}