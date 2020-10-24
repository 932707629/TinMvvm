package ${viewModelPackageName}

import android.app.Application
import me.soushin.tinmvvm.base.BaseViewModel
import ${packageName}.mvvm.model.${pageName}Model

<#import "root://activities/TinMvvmTemplate/globals.xml.ftl" as gb>
<@gb.fileHeader />

class ${pageName}ViewModel(application: Application) :
       BaseViewModel<${pageName}Model>(application,${pageName}Model()) {



}
