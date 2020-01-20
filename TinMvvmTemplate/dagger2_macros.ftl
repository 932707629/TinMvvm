<#macro addAllKaptDependencies>
  <#if !isNewProject && ((language!'Java')?string == 'Kotlin')>
    <apply plugin="kotlin-kapt" />
  </#if>
</#macro>
