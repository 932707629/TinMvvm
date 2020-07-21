package me.soushin.tinmvvm.base

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import me.soushin.tinmvvm.utils.LogUtils
import java.lang.reflect.ParameterizedType

/**
 * activity基类
 * 可以添加一些常用的基础方法 或者实现方法
 * 不建议这里做太多逻辑操作
 * @author created by Soushin
 * @time 2020/1/7 13 29
 */
abstract class BaseActivity<V : ViewDataBinding,VM: BaseViewModel<*>> :AppCompatActivity(){

    protected var dataBinding:V?=null
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
                dataBinding= DataBindingUtil.setContentView(this,layoutId)
                dataBinding?.lifecycleOwner=this
                viewModel=ViewModelProviders.of(this).get(viewModel())
                viewModel?.injectLifecycleOwner(this)
                dataBinding?.setVariable(initVariableId(),viewModel)
            }
        }catch (e:Exception){
            LogUtils.i("viewmodel初始化异常${e.message}");
            e.printStackTrace()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        //设置字体为默认大小，不随系统字体大小改而改变
        LogUtils.i("用户字体大小有改变onConfigurationChanged", newConfig.fontScale)
        if (newConfig.fontScale != 1f) {
            resources
        }
        super.onConfigurationChanged(newConfig)
    }

    override fun getResources(): Resources {
        val res = super.getResources()
        if (res.configuration.fontScale != 1f) { //非默认值
            LogUtils.i("用户字体大小有改变getResources", res.configuration.fontScale)
            val newConfig = Configuration()
            newConfig.setToDefaults() //设置默认
            createConfigurationContext(newConfig)
//            res.updateConfiguration(newConfig, res.displayMetrics)//方法废弃
        }
        return res
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