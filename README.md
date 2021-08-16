# TinMvvm #

## Surprise[![](https://jitpack.io/v/932707629/TinMvvm.svg)](https://jitpack.io/#932707629/TinMvvm)

```

   //添加jitpack地址到项目根目录的build.gradle
    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
    //添加依赖到mudule目录的build.gradle
	dependencies {
    	implementation 'com.github.932707629:TinMvvm:1.2.4'
    }
    
```

### 说在前面 ###
虽然已经在项目中添加了比较详细的代码注释，但是肯定还有很多需要注意的地方没有解释清楚，希望大家在使用的过程中如果发现了什么问题，及时提出来，大家共同解决.

### 我们的优势 ###
TinMvvm是以谷歌DataBinding+LiveData+ViewModel+Navigation框架为基础，整合Rxjava2、ViewBinding、okhttp-RxHttp、Liveeventbus等流行模块，加上各种原生控件，
让事件与数据源完美绑定的一款容易上瘾的实用性MVVM快速开发框架。从此告别findViewById()，告别set()、get()；有了Navigation的助力，
支持单Activity多Fragment，当然，您也可以继续使用多Activity模式。

[更多Jetpack组件即将来袭！](https://developer.android.google.cn/jetpack "更多Jetpack组件即将来袭")

### 功能列表 ###

- 加入navigation组件，支持单Activity多Fragment模式
- 解决屏幕适配问题，适配全面屏/刘海屏(AndroidAutoSize是代替AndroidAutoLayout的屏幕适配框架，原理是基于今日头条的适配方案)
- 代码解耦，提供ActivityLifecycleCallbacksImpl、FragmentLifecycleCallbacksImpl给baseActivity/baseFragment减压
- 使用堆栈对Activity进行统一管理，AppManager封装了各种常用方法
- Activity标题栏统一设置，支持标题栏和状态栏统一设置背景color/shape/drawable
- 支持fragment设置状态栏沉浸式，多fragment无缝切换
- 提供fragmentUtils工具类，解决fragment重叠的bug，fragment任务栈统一管理
- 提供懒加载onLazyInitView()/fragment可见性onInvisible()/onVisible()方法回调
- BaseRecyclerViewAdapter3.0加持，使用ViewBinding，很nice！！！
- 使用RxHttp网络框架链式调用，与Rxjava3相结合，线程智能控制（开发者可选retrofit替换 个人比较喜欢rxhttp独特的设计）
- 解决Toast禁用通知权限不能弹出的bug，不分主次线程，可自定义Toast样式
- 全局使用Kotlin，可与Java无缝转换
- 支持新手指引，编程中使用不规范的地方，会引导你正确使用
- 使用插件一键生成Activity/Fragment
- 依赖RxPermission，权限申请更简单
- 使用LiveEventBus，自动销毁，事件传递更加清晰
- 异常捕获机制，将捕获到的异常信息反馈给开发者（Rxjava3异常以及运行时异常）
- 已废弃kotlin-android-extensions插件，完全支持databinding和viewbinding混合使用
- 新增BaseService，扩展了对Service的支持，使用Rxjava以及协程会更方便一些
- MMKV优化键值对存储，是原生SharedPreferences写入速度的数十倍(DataStore目前还不稳定,且性能不如MMKV,以后可能会考虑换用DataStore)



### 开发准备 ###

必须的项目配置，框架初始化都已添加，所以直接复制本项目更改包名，即可进行开发使用

### 开发指南 ###

使用的第三方框架:

[Jetpack组件库](https://developer.android.google.cn/jetpack "Jetpack组件库")

[Navigation](https://developer.android.google.cn/jetpack/androidx/releases/navigation "Navigation")

[RxJava3](https://github.com/ReactiveX/RxJava "RxJava3")

[ImmersionBar](https://github.com/gyf-dev/ImmersionBar "ImmersionBar")

[AndroidAutoSize](https://github.com/JessYanCoding/AndroidAutoSize)

[okhttp](https://github.com/square/okhttp "okhttp")

[ToastUtils](https://github.com/getActivity/ToastUtils "ToastUtils")

[BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper "BaseRecyclerViewAdapterHelper")

[ALog](https://github.com/Blankj/ALog "ALog")

[RxPermissions](https://github.com/tbruyelle/RxPermissions "RxPermissions")

[MMKV](https://github.com/Tencent/MMKV "MMKV")

### 使用插件一键生成Activity/Fragment ###

Android Studio4.0以上不支持自定义模板,可以使用[一键生成TinMvvm组件](https://github.com/932707629/tin-mvvm-template)

Android Studio4.0以下使用TinMvvmTemplate这个文件里放着TinMvv的一键生成工具

可将TinMvvmTemplate放到AS安装目录\plugins\android\lib\templates\activities里

放进去之后重启AS，然后在需要建Activity/Fragment的地方，右键--New--Activity--TinMvvm全家桶，可选Activity/Fragment，然后取个名字，点击Finish，欧克，打完收功!

### 注意: ###

- ActivityLifeCycleCallBackIml可以监听到整个项目所有Activity的生命周期(包括第三方)，所以可以用它执行一些初始化与销毁业务，实现了对BaseActivity的解耦，FragmentLifecycleCallbacksImpl同理

- 如果要在fragment里设置状态栏沉浸，可以让该fragment实现SimpleImmersionOwner接口，或者实现ImmersionOwner接口，具体实现可以参考ImmersionBar的demo使用

- 三方库的初始化是在AppLifecycleImpl里进行的，建议能在子线程初始化的在子线程初始化.

- 本框架默认使用kotlin构建，如果您使用的是java，请自行依赖butterknife等第三方组件.

- 实际使用中发现[AndroidAutoSize](https://www.jianshu.com/p/55e0fca23b4f "骚年你的屏幕适配方式该升级了")对于第三方view的支持不是很友好，
比如穿山甲的广告View，会直接导致View比例显示异常，虽然可以用副单位去解决，但是要把整个页面都换成副单位，个人感觉不太友好，
所以有这种项目需要的童鞋要谨慎使用哦，可以考虑换用[最小宽度限定符](https://www.jianshu.com/p/2aded8bb6ede "骚年你的屏幕适配方式该升级了")

- 结合NavigationUI和BottomNavigationView使用有异常，fragment之间相互重叠，而且每次切换还会重新初始化fragment
  答：Navigation 的官方使用方式是每次显示一个Fragment都会重新去创建页面去显示(跳转一个新页面或者回退到上一页面)，
  例如返回页面的状态恢复，Jetpack提供了一系列的状态保存方案（Navigation2.4.0-alpha版本对BottomNavigationView状态保存也提供了支持）。

### 新版功能预览:

- 修复fragment+tablayout使用时会重复创建的问题
- jitpack发布新版本
- 修复HttpHandleCallBack添加生命周期造成的强转异常
- 修复navigation导致fragment重复创建的问题```smooth-navigation```
- 升级到Rxjava3，相应的RxPermissions版本升级到0.12，取消依赖RxErrorHandle(不支持RxJava3)依赖

### 遇到的问题
- 当DemoActivity用到ViewPager2的时候去设置setOffscreenPageLimit() 此时fragment转场到下一个fragment时可能会显示空白?
比如在此项目示例中设置了setOffscreenPageLimit()后点击HomeFragment的MultiplexFragment/RecyclerFragment/TabLayoutViewpager2Fragment三个页面可能会空白
这三个页面都跟RecyclerFragment的页面有关系 

### 我的主页 ###

 - 简书:[敲代码的鱼](https://www.jianshu.com/u/db6ff36dac08 "敲代码的鱼")
 - 掘金:[敲代码的鱼](https://juejin.cn/user/1865248698012616 "敲代码的鱼")
 - CSDN:[敲代码的鱼哇](https://blog.csdn.net/qq_35195386?spm=1001.2101.3001.5343 "敲代码的鱼哇")




