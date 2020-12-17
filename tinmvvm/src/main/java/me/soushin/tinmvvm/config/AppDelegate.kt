package me.soushin.tinmvvm.config

import android.app.Application
import android.content.Context
import me.soushin.tinmvvm.listener.AppLifecycle
import me.soushin.tinmvvm.listener.ConfigModule
import me.soushin.tinmvvm.utils.ManifestParser

/**
 * AppDelegate 可以代理 Application 的生命周期,在对应的生命周期,执行对应的逻辑,因为 Java 只能单继承
 * 所以当遇到某些三方库需要继承于它的 Application 的时候,就只有自定义 Application 并继承于三方库的 Application
 * 这时就不用再继承 BaseApp,只用在自定义Application中对应的生命周期调用AppDelegate对应的方法
 * (Application一定要实现APP接口),框架就能照常运行
 *
 * @auther SouShin
 * @time 2020/7/15 13:35
 */
class AppDelegate//用反射, 将 AndroidManifest.xml 中带有 ConfigModule 标签的 class 转成对象集合（List<ConfigModule>）
    (ctx: Context) : AppLifecycle {

    private var modules:MutableList<ConfigModule>?=null
    private val mAppLifecycles= mutableListOf<AppLifecycle>()
    private val mActivityLifecycle= mutableListOf<Application.ActivityLifecycleCallbacks>()

    init {

        this.modules=ManifestParser(ctx).parse()
        modules?.forEach {
            it.injectActivityLifecycle(ctx,mActivityLifecycle)
            it.injectAppLifecycle(ctx,mAppLifecycles)
        }
    }

    override fun attachBaseContext(base: Context) {
        mAppLifecycles.forEach {
            it.attachBaseContext(base)
        }
    }

    override fun onCreate(application: Application) {

        modules?.let {
            val globalConfig=getGlobalConfigModule(application,it)
            AppComponent(globalConfig)//这里不发推送了  发了里面也没啥东西
            /*//只要有lifecyclerower的地方就可以获取功能组件
            LiveEventBus.get(LiveDataTag.tag_globalConfig)
                .post(AppComponent(globalConfig))*/
        }
        //注册框架内部所需要的Activity 生命周期逻辑
        application.registerActivityLifecycleCallbacks(TinActivityLifecycleImpl())

        //注册框架外部, 开发者扩展的 Activity 生命周期逻辑
        //每个 ConfigModule 的实现类可以声明多个 Activity 的生命周期回调
        //也可以有 N 个 ConfigModule 的实现类 (完美支持组件化项目 各个 Module 的各种独特需求)
        mActivityLifecycle.forEach {
            application.registerActivityLifecycleCallbacks(it)
        }
        //执行框架外部, 开发者扩展的 App onCreate 逻辑
        mAppLifecycles.forEach {
            it.onCreate(application)
        }
    }

    override fun onTerminate(application: Application) {
        mActivityLifecycle.forEach {
            application.unregisterActivityLifecycleCallbacks(it)
        }
        mAppLifecycles.forEach {
            it.onTerminate(application)
        }
    }

    /**
     * 将app的全局配置信息封装进module(使用Dagger注入到需要配置信息的地方)
     * 需要在AndroidManifest中声明[ConfigModule]的实现类,和Glide的配置方式相似
     *
     * @return GlobalConfigModule
     */
    private fun getGlobalConfigModule(
        context: Context,
        modules: MutableList<ConfigModule>
    ): GlobalConfigModule {
        val builder: GlobalConfigModule.Builder = GlobalConfigModule.Builder()

        //遍历 ConfigModule 集合, 给全局配置 GlobalConfigModule 添加参数
        for (module in modules) {
            module.applyOptions(context, builder)
        }
        return builder.build()
    }

}