package me.soushin.tinmvvm.base

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.blankj.ALog
import com.gyf.immersionbar.components.ImmersionOwner
import com.gyf.immersionbar.components.ImmersionProxy
import me.soushin.tinmvvm.config.DataBindingConfig
import me.soushin.tinmvvm.utils.inflateBindingWithGeneric
import java.lang.reflect.ParameterizedType

/**
 * fragment基类封装
 * @author created by Soushin
 * @time 2020/1/14 15 25
 *
 */
 abstract class DataBindingFragment<VD : ViewDataBinding, VM: BaseViewModel<out BaseRepository>>  : Fragment(),ImmersionOwner {

    protected var mViewModel:VM?=null
    protected var mViewData:VD?=null
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
        initView(inflater,container,savedInstanceState)
        return mViewData?.root
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
        mViewData=null
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

    protected fun dataViewBinding(inflater: LayoutInflater, container: ViewGroup?):VD? {
        var vd:VD?=null
        getDataBindingConfig()?.apply {
            //如果当前页面的layoutId为空就不会去调用setContentView()实例化mViewData
            layoutId?.let {
                vd = DataBindingUtil.inflate<VD>(inflater,it,container,false)
                vd!!.lifecycleOwner = this@DataBindingFragment
                bindingParams.forEach {entry->
                    vd!!.setVariable(entry.key,entry.value)
                }
            }
            //如果当前页面的vmClass为空就不会去实例化mViewModel
            vmClass?.let {
                mViewModel = ViewModelProvider(this@DataBindingFragment)[it as Class<VM>]
                mViewModel!!.registerLifecycleOwner(this@DataBindingFragment)
                lifecycle.addObserver(mViewModel!!)
                //如果当前页面设置的variableId为空就不会去绑定ViewModel
                variableId?.let {vid->
                    vd?.setVariable(vid,mViewModel)
                }
            }
        }
        return vd
    }

    abstract fun initView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?)

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


}