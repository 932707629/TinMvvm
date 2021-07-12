package me.soushin.tinmvvm.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import me.soushin.tinmvvm.utils.inflateBindingWithGeneric
import java.lang.reflect.ParameterizedType

/**
 * activity基类
 * 可以添加一些常用的基础方法 或者实现方法
 * 不建议这里做太多逻辑操作
 * @author created by Soushin
 * @time 2020/1/7 13 29
 */
abstract class BaseActivity<VD : ViewDataBinding,VM: BaseViewModel<*>> :AppCompatActivity(){

    protected var viewModel:VM?=null
    protected var viewData:VD? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataViewBinding()
        initView(savedInstanceState)
    }

    override fun onDestroy() {
        viewData=null
        super.onDestroy()
    }

    /**
     * DataBindingUtil.bind和DataBindingUtil.setContentView两种方法有毫秒级的差距 bind+setContentView更快
     * 用DataBindingUtil.bind可以用反射 用DataBindingUtil.setContentView还需要上层指定一个layoutId
     * 总结:还是bind方法更简单一些
     */
    private fun dataViewBinding() {
//        val viewBinding=inflateBindingWithGeneric<VD>(layoutInflater)
//        viewData=DataBindingUtil.bind(viewBinding.root)
        viewData=inflateBindingWithGeneric<VD>(layoutInflater)
        viewData?.let {
            setContentView(it.root)
            viewData?.lifecycleOwner=this
            viewModel= ViewModelProvider(this)[viewModel()]
            viewModel?.registerLifecycleOwner(this)
            lifecycle.addObserver(viewModel!!)
            it.setVariable(initVariableId(),viewModel)
        }
    }


    @SuppressWarnings("unchecked")
    private fun viewModel() :Class<VM>{
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

    /**
     * 返回值可以为0 即不设置setContentView
     */
//    abstract fun initView(savedInstanceState: Bundle?):Int

    abstract fun initView(savedInstanceState: Bundle?)

    abstract fun initVariableId():Int

    /**
     * 开启对fragment的生命周期监听
     * 可以在这里做一些baseFragment的操作
     */
    open fun useFragment():Boolean{
        return true
    }



}