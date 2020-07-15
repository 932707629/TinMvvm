package me.soushin.tinmvvm.config

/**
 * 拥有此类即可调用对应的方法拿到对应实例
 * @auther SouShin
 * @time 2020/7/15 16:37
 */
class AppComponent {

    val globalConfig: GlobalConfigModule

    constructor(globalConfig: GlobalConfigModule){
        this.globalConfig=globalConfig
        // TODO: 2020/7/15  这里拿到全局配置信息即可实现对全局参数统一配置
    }





}