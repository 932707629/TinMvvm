package ${ativityPackageName}

import android.content.Intent
import android.os.Bundle

import com.jess.arms.di.component.AppComponent

import ${componentPackageName}.Dagger${pageName}Component
import ${moudlePackageName}.${pageName}Module
import ${contractPackageName}.${pageName}Contract
import ${presenterPackageName}.${pageName}Presenter

import ${packageName}.R


<#import "root://activities/MVPArmsTemplate/globals.xml.ftl" as gb>

<@gb.fileHeader />
/**
 * 如果没presenter
 * 你可以这样写
 *
 * @ActivityScope(請注意命名空間) class NullObjectPresenterByActivity
 * @Inject constructor() : IPresenter {
 * override fun onStart() {
 * }
 *
 * override fun onDestroy() {
 * }
 * }
 */
class ${pageName}Activity : BaseActivity<${pageName}Presenter>() , ${pageName}Contract.View {

    override fun setupActivityComponent(appComponent:AppComponent) {
        Dagger${pageName}Component //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .${extractLetters(pageName[0]?lower_case)}${pageName?substring(1,pageName?length)}Module(${pageName}Module(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState:Bundle?):Int {
              return R.layout.${activityLayoutName} //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    override fun initData(savedInstanceState:Bundle?) {

    }

    override fun launchActivity(intent: Intent) {
        super<BaseActivity>.launchActivity(intent)
    }

    override fun showLoading() {
        super<BaseActivity>.showLoading()
    }

    override fun hideLoading() {
        super<BaseActivity>.hideLoading()
    }

    override fun killMyself() {
        super<BaseActivity>.killMyself()
    }

}
