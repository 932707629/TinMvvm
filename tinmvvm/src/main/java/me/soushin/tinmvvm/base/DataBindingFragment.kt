package me.soushin.tinmvvm.base

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.gyf.immersionbar.components.ImmersionOwner
import com.gyf.immersionbar.components.ImmersionProxy
import kotlinx.coroutines.cancel
import me.soushin.tinmvvm.config.AppComponent
import me.soushin.tinmvvm.config.DataBindingConfig
import me.soushin.tinmvvm.custom.SharedViewModelStore

/**
 * fragment基类封装
 * @author created by Soushin
 * @time 2020/1/14 15 25
 *
 */
 abstract class DataBindingFragment<VD : ViewDataBinding,
        VM: BaseViewModel<out BaseRepository>>  : Fragment(),ImmersionOwner {

    protected var mViewModel:VM?=null
    protected var mViewData:VD?=null
    //通过ViewModelProvider可以获取同一个Activity下共享的ViewModel
    protected var mViewModelProvider:ViewModelProvider?=null
    var mContext: Context? = null

    private val mImmersionProxy by lazy { ImmersionProxy(this) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext=context
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        mImmersionProxy.isUserVisibleHint = isVisibleToUser
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mImmersionProxy.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mViewData = dataViewBinding(inflater,container)
        return mViewData?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view,savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mImmersionProxy.onActivityCreated(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        mImmersionProxy.onResume()
    }

    override fun onPause() {
        super.onPause()
        mImmersionProxy.onPause()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        mImmersionProxy.onHiddenChanged(hidden)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mImmersionProxy.onConfigurationChanged(newConfig)
    }

    override fun onDestroyView() {
        mViewData?.unbind()
        mViewData=null
        lifecycleScope.cancel()//取消协程事件
        super.onDestroyView()
    }

    override fun onDestroy() {
        mImmersionProxy.onDestroy()
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
        this.mContext=null
    }

    open fun dataViewBinding(inflater: LayoutInflater, container: ViewGroup?):VD? {
        var vd:VD?=null
        getDataBindingConfig()?.apply {
            //如果当前页面的layoutId为空就不会去调用setContentView()实例化mViewData
            layoutId?.let {
                vd = if (dataBindingComponent!=null && dataBindingComponent is DataBindingComponent){
                    DataBindingUtil.inflate<VD>(inflater,it,container,false,dataBindingComponent as DataBindingComponent)
                }else {
                    DataBindingUtil.inflate<VD>(inflater,it,container,false)
                }
                vd!!.lifecycleOwner = this@DataBindingFragment
                bindingParams.forEach {entry->
                    vd!!.setVariable(entry.key,entry.value)
                }
            }
            //如果当前页面的vmClass为空就不会去实例化mViewModel
            vmClass?.let {
                mViewModelProvider = if (sharedViewModel()) ViewModelProvider(AppComponent.sharedViewModelStore, defaultViewModelProviderFactory) else ViewModelProvider(this@DataBindingFragment)
                mViewModel = mViewModelProvider!![it] as VM
                lifecycle.addObserver(mViewModel!!)
                //如果当前页面设置的variableId为空就不会去绑定ViewModel
                variableId?.let {vid->
                    vd?.setVariable(vid,mViewModel)
                }
                //为防止直接在DataBindingFragment.initView()调用时出现为空的情况
                if (mViewModel!!.lifecycle == null){
                    mViewModel!!.lifecycle = this@DataBindingFragment
                }
            }
        }
        return vd
    }

    abstract fun initView(view:View,savedInstanceState: Bundle?)

    abstract fun getDataBindingConfig(): DataBindingConfig?

    /****************提供可重写方法******************/

    //懒加载，在view初始化完成之后执行
    override fun onLazyAfterView() {}
    //懒加载，在view初始化完成之前执行
    override fun onLazyBeforeView() {}
    //Fragment用户不可见时候调用
    override fun onInvisible() {}
    //Fragment用户可见时候调用
    override fun onVisible() {}
    //是否可以实现沉浸式，当为true的时候才可以执行initImmersionBar方法
    override fun immersionBarEnabled(): Boolean { return false }
    //状态栏设置
    override fun initImmersionBar() {}

    /**
     * 开启ViewModel共享数据
     * true mViewModel实例将于其他Activity、Fragment共享（包括VM中创建的实例）
     * false mViewModel实例不会与其他页面共享
     */
    open fun sharedViewModel():Boolean{
        return true
    }

}