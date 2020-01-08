package com.soushin.tinmvvm.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import java.lang.reflect.ParameterizedType

/**
 * activity基类
 * 可以添加一些常用的基础方法 或者实现方法
 * 不建议这里做太多逻辑操作
 * @author created by Soushin
 * @time 2020/1/7 13 29
 */
    abstract class BaseActivity<V : ViewDataBinding, VM: BaseViewModel<*>> :AppCompatActivity(){

    protected var dataBinding:V?=null
    protected var viewModel:VM?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layoutId= initView(savedInstanceState)
        if (layoutId!=0){
            dataBinding= DataBindingUtil.setContentView(this,layoutId)
            dataBinding?.lifecycleOwner=this
            val type=javaClass.genericSuperclass
            val modelClass= run {
                if (type is ParameterizedType){
                    type.actualTypeArguments[1] as Class<VM>
                }else {
                    BaseViewModel::class.java as Class<VM>
                }
            }
            viewModel=ViewModelProviders.of(this).get(modelClass)
            dataBinding?.setVariable(initVariableId(),viewModel)
        }
        initData(savedInstanceState)
    }

    override fun onDestroy() {
        viewModel?.cancel()
        super.onDestroy()
    }

//    inline fun <reified T : ViewModel> viewModel() =
//        lazy { ViewModelProviders.of(this).get(T::class.java) }

    /**
     * 返回值可以为0 即不设置setContentView
     */
    abstract fun initView(savedInstanceState: Bundle?):Int

    abstract fun initData(savedInstanceState: Bundle?)

    abstract fun initVariableId():Int


}