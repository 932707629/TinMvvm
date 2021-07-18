package me.soushin.tinmvvm.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import me.soushin.tinmvvm.R
import me.soushin.tinmvvm.config.DataBindingConfig
import me.soushin.tinmvvm.utils.inflateBindingWithGeneric
import java.lang.reflect.ParameterizedType

/**
 * activity基类
 * 可以添加一些常用的基础方法 或者实现方法
 * 不建议这里做太多逻辑操作
 * @author created by Soushin
 * @time 2020/1/7 13 29
 */
abstract class DataBindingActivity<VD : ViewDataBinding,VM : BaseViewModel<out BaseRepository>> :AppCompatActivity(){

    protected var mViewModel:VM? = null
    protected var mViewData:VD? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataViewBinding()
        initView(savedInstanceState)
    }

    override fun onDestroy() {
        mViewData=null
        super.onDestroy()
    }

    /**
     * DataBindingUtil.bind和DataBindingUtil.setContentView两种方法有毫秒级的差距 bind+setContentView更快
     * 用DataBindingUtil.bind需要用反射 用DataBindingUtil.setContentView需要上层指定一个layoutId
     */
    private fun dataViewBinding() {
        getDataBindingConfig()?.apply {
            //如果当前页面的layoutId为空就不会去调用setContentView()实例化mViewData
            layoutId?.let {
                mViewData = DataBindingUtil.setContentView(this@DataBindingActivity, it)
                bindingParams.forEach {entry->
                    mViewData!!.setVariable(entry.key,entry.value)
                }
            }
            //如果当前页面的vmClass为空就不会去实例化mViewModel
            vmClass?.let {
                mViewModel = ViewModelProvider(this@DataBindingActivity)[it as Class<VM>]
                lifecycle.addObserver(mViewModel!!)
                //如果当前页面设置的variableId为空就不会去绑定ViewModel
                variableId?.let {vid->
                    mViewData?.setVariable(vid,mViewModel)
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



}