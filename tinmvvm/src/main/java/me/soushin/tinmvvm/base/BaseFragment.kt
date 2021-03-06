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
import androidx.lifecycle.ViewModelProviders
import com.gyf.immersionbar.components.ImmersionOwner
import com.gyf.immersionbar.components.ImmersionProxy
import me.soushin.tinmvvm.utils.inflateBindingWithGeneric
import java.lang.reflect.ParameterizedType

/**
 * fragment基类封装
 * @author created by Soushin
 * @time 2020/1/14 15 25
 */
 abstract class BaseFragment<VD : ViewDataBinding, VM: BaseViewModel<*>>  : Fragment(),ImmersionOwner {

    var mContext: Context? = null
    protected var viewData:VD?=null
    protected var viewModel:VM?=null
    private val mImmersionProxy by lazy { ImmersionProxy(this) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext=context
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
//        childFragmentManager.beginTransaction().setMaxLifecycle()
        mImmersionProxy.isUserVisibleHint = isVisibleToUser
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mImmersionProxy.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (viewData==null){
            val viewBinding=inflateBindingWithGeneric<VD>(inflater,container,false)
            dataViewBinding(viewBinding.root)
            initView(savedInstanceState)
        }
        return viewData!!.root
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

    override fun onDestroy() {
        super.onDestroy()
        mImmersionProxy.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
        this.mContext=null
    }

//    abstract fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):View

    abstract fun initView(savedInstanceState: Bundle?)

    abstract fun initVariableId():Int

    override fun onLazyAfterView() {
        //懒加载，在view初始化完成之后执行
    }

    override fun onLazyBeforeView() {
        //懒加载，在view初始化完成之前执行
    }

    override fun onInvisible() {
        //Fragment用户不可见时候调用
    }

    override fun onVisible() {
        //Fragment用户可见时候调用
    }

    override fun immersionBarEnabled(): Boolean {
        //是否可以实现沉浸式，当为true的时候才可以执行initImmersionBar方法
        return false
    }

    override fun initImmersionBar() {
        //状态栏设置
    }
    protected fun dataViewBinding(view: View) {
        viewData= DataBindingUtil.bind(view)
        viewData?.lifecycleOwner=this
        viewModel= ViewModelProviders.of(this).get(viewModel())
        viewModel?.registerLifecycleOwner(this)
        lifecycle.addObserver(viewModel!!)
        viewData?.setVariable(initVariableId(),viewModel)
    }

    @SuppressWarnings("unchecked")
    protected fun viewModel() :Class<VM>{
        val type=javaClass.genericSuperclass
        return run {
            if (type is ParameterizedType){
                @Suppress("UNCHECKED_CAST")
                type.actualTypeArguments[1] as Class<VM>
            }else {
                @Suppress("UNCHECKED_CAST")
                BaseViewModel::class.java as Class<VM>
            }
        }
    }

}