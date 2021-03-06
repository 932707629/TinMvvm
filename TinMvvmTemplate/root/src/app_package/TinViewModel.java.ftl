package ${viewModelPackageName};

import android.app.Application;
import me.soushin.tinmvvm.base.BaseViewModel;
import ${packageName}.mvvm.model.${pageName}Model;
import org.jetbrains.annotations.NotNull;

<#import "root://activities/TinMvvmTemplate/globals.xml.ftl" as gb>
<@gb.fileHeader />

public class ${pageName}ViewModel extends BaseViewModel<${pageName}Model> {

    public ${pageName}ViewModel(@NotNull Application application) {
        super(application, new ${pageName}Model());
    }

}
