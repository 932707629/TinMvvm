package me.soushin.tinmvvm.base

import android.app.Activity
import android.app.Application
import androidx.annotation.NonNull
import androidx.annotation.RestrictTo
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.rxjava.rxlife.Scope
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.lang.ref.WeakReference
import kotlin.coroutines.CoroutineContext

/**
 * @author created by Soushin
 * @time 2020/1/7 16 38
 */
open class BaseViewModel<M: BaseModel>(application: Application, val model: M) : AndroidViewModel(
    application
), CoroutineScope, Scope,
    LifecycleEventObserver {

    private var mCompositeDisposable: CompositeDisposable?=null

    /**
     * lifecycleOwner实际传入的是上下文对象的软引用
     * 可以将其强转为上下文对象来使用
     */
    protected var lifecycleOwner: WeakReference<LifecycleOwner>?=null
    private val job = Job()

    //这里可以让basemodel具有协程的功能
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    fun addSubcribe(dis: Disposable){
        if (mCompositeDisposable==null){
            this.mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable?.add(dis)
    }

    /**
     * 生命周期注入
     */
    fun injectLifecycleOwner(@NonNull lifecycleOwner: LifecycleOwner){
        this.lifecycleOwner= WeakReference(lifecycleOwner)
        lifecycleOwner.lifecycle.addObserver(this)
    }

    override fun onCleared() {
        super.onCleared()//会跟随页面生命周期销毁
        job.cancel()
    }

    override fun onScopeEnd() {

    }

    override fun onScopeStart(d: Disposable) {
        addSubcribe(d)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        //Activity/Fragment 生命周期回调
        if (event == Lifecycle.Event.ON_DESTROY) {  //Activity/Fragment 销毁
//            println("管道中断....")
            source.lifecycle.removeObserver(this)
            dispose() //中断RxJava管道
        }
    }

    /**
     * 生命周期对象实际上传入的是对应的Activity/Fragment
     * 如果lifecycleOwner为空或者当前非Activity上下文可为空
     */
    fun getActivity() :Activity?{
        if (lifecycleOwner?.get() is Activity){
            return lifecycleOwner?.get() as Activity
        }
        return null
    }

    /**
     * 生命周期对象实际上传入的是对应的Activity/Fragment
     * 如果lifecycleOwner为空或者当前非Fragment上下文可为空
     */
    fun getFragment():Fragment?{
        if (lifecycleOwner?.get() is Fragment){
            return lifecycleOwner?.get() as Fragment
        }
        return null
    }

    open fun dispose() {
        this.mCompositeDisposable?.dispose()
    }

}