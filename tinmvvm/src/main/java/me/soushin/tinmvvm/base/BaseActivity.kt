package me.soushin.tinmvvm.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import java.lang.reflect.ParameterizedType

/**
 * activity基类
 * 可以添加一些常用的基础方法 或者实现方法
 * 不建议这里做太多逻辑操作
 * @author created by Soushin
 * @time 2020/1/7 13 29
 */
abstract class BaseActivity<VD : ViewDataBinding,VM: BaseViewModel<*>> :AppCompatActivity(){

    protected var viewData:VD?=null
    protected var viewModel:VM?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layoutId= initView(savedInstanceState)
        dataViewBinding(layoutId)
        initData(savedInstanceState)
    }

    private fun dataViewBinding(layoutId:Int) {
        try {
            if (layoutId!=0){
                viewData= DataBindingUtil.setContentView(this,layoutId)
                viewData?.lifecycleOwner=this
                viewModel=ViewModelProviders.of(this).get(viewModel())
                viewModel?.injectLifecycleOwner(this)
                viewData?.setVariable(initVariableId(),viewModel)
            }
        }catch (e:Exception){
            println("viewmodel初始化异常${e.message}")
            e.printStackTrace()
        }
    }

    private fun viewModel() :Class<VM>{
        val type=javaClass.genericSuperclass
        return run {
            if (type is ParameterizedType){
                var i=1
                type.actualTypeArguments.forEachIndexed { index, type ->
                    if (type is ViewModel){ i=index }
                }
                @Suppress("UNCHECKED_CAST")
                type.actualTypeArguments[i] as Class<VM>
            }else {
                @Suppress("UNCHECKED_CAST")
                BaseViewModel::class.java as Class<VM>
            }
        }
    }

    /**
     * 返回值可以为0 即不设置setContentView
     */
    abstract fun initView(savedInstanceState: Bundle?):Int

    abstract fun initData(savedInstanceState: Bundle?)

    abstract fun initVariableId():Int

    /**
     * 开启对fragment的生命周期监听
     * 可以在这里做一些baseFragment的操作
     */
    open fun useFragment():Boolean{
        return false
    }



}