package me.soushin.tinmvvm.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.cancel
import me.soushin.tinmvvm.config.AppComponent
import me.soushin.tinmvvm.config.DataBindingConfig

/**
 * fragment基类封装
 * @author created by Soushin
 * @time 2020/1/14 15 25
 *
 */
 abstract class DataBindingFragment<VD : ViewDataBinding,
        VM: BaseViewModel<out BaseRepository>>  : Fragment() {

    var mViewModel:VM?=null
    var mViewData:VD?=null
    //通过ViewModelProvider可以获取同一个Activity下共享的ViewModel
    var mViewModelProvider:ViewModelProvider?=null
    var mContext: Context? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext=context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mViewData = dataViewBinding(inflater,container)
        return mViewData?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view,savedInstanceState)
    }

    override fun onDestroyView() {
        mViewData?.unbind()
        mViewData=null
        lifecycleScope.cancel()//取消协程事件
        super.onDestroyView()
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
                vd!!.lifecycleOwner = vd!!.root.findViewTreeLifecycleOwner()
                bindingParams.forEach {entry->
                    vd!!.setVariable(entry.key,entry.value)
                }
            }
            //如果当前页面的vmClass为空就不会去实例化mViewModel
            vmClass?.let {
                mViewModelProvider = if (sharedViewModel()) ViewModelProvider(AppComponent.sharedViewModelStore, defaultViewModelProviderFactory)
                else ViewModelProvider(this@DataBindingFragment)
                mViewModel = mViewModelProvider!![it] as VM
                lifecycle.addObserver(mViewModel!!)
                //如果当前页面设置的variableId为空就不会去绑定ViewModel
                variableId?.let {vid->
                    vd?.setVariable(vid,mViewModel)
                }
                //为防止直接在DataBindingFragment.initView()调用时出现为空的情况
                mViewModel?.lifecycle = vd?.lifecycleOwner
            }
        }
        return vd
    }

    abstract fun initView(view:View,savedInstanceState: Bundle?)

    abstract fun getDataBindingConfig(): DataBindingConfig?

    /**
     * 开启ViewModel共享数据
     * true mViewModel实例将于其他Activity、Fragment共享（包括VM中创建的实例）
     * false mViewModel实例不会与其他页面共享
     */
    open fun sharedViewModel():Boolean{
        return AppComponent.globalConfig?.sharedViewModel ?: false
    }

}