<?xml version="1.0"?>
<#import "root://activities/common/kotlin_macros.ftl" as kt>
<#import "root://activities/TinMvvmTemplate/dagger2_macros.ftl" as dagger2>
<recipe>
<@kt.addAllKotlinDependencies />
<@dagger2.addAllKaptDependencies />
<#if needActivity>
    <merge from="root/AndroidManifest.xml.ftl"
           to="${escapeXmlAttribute(manifestOut)}/AndroidManifest.xml" />
</#if>

<#if needActivity && generateActivityLayout>
    <instantiate from="root/res/layout/simple.xml.ftl"
                 to="${escapeXmlAttribute(resOut)}/layout/${activityLayoutName}.xml" />
</#if>

<#if needFragment && generateFragmentLayout>
    <instantiate from="root/res/layout/simple.xml.ftl"
                 to="${escapeXmlAttribute(resOut)}/layout/${fragmentLayoutName}.xml" />
</#if>

<#if needActivity>
    <instantiate from="root/src/app_package/TinActivity.${ktOrJavaExt}.ftl"
                   to="${projectOut}/src/main/java/${slashedPackageName(ativityPackageName)}/${pageName}Activity.${ktOrJavaExt}" />
    <open file="${projectOut}/src/main/java/${slashedPackageName(ativityPackageName)}/${pageName}Activity.${ktOrJavaExt}" />
</#if>

<#if needFragment>
    <instantiate from="root/src/app_package/TinFragment.${ktOrJavaExt}.ftl"
                   to="${projectOut}/src/main/java/${slashedPackageName(fragmentPackageName)}/${pageName}Fragment.${ktOrJavaExt}" />
    <open file="${projectOut}/src/main/java/${slashedPackageName(fragmentPackageName)}/${pageName}Fragment.${ktOrJavaExt}" />
</#if>


<#if needViewModel>
    <instantiate from="root/src/app_package/TinViewModel.${ktOrJavaExt}.ftl"
                   to="${projectOut}/src/main/java/${slashedPackageName(viewModelPackageName)}/${pageName}ViewModel.${ktOrJavaExt}" />
    <open file="${projectOut}/src/main/java/${slashedPackageName(viewModelPackageName)}/${pageName}ViewModel.${ktOrJavaExt}" />
</#if>

<#if needModel>
    <instantiate from="root/src/app_package/TinModel.${ktOrJavaExt}.ftl"
                   to="${projectOut}/src/main/java/${slashedPackageName(modelPackageName)}/${pageName}Model.${ktOrJavaExt}" />
</#if>


</recipe>
