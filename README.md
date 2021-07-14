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
支持单Activity多Fragment，当然，您也可以继续试用多Activity模式，Navigation同样支持。

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
- brvh3.0+，使用viewbinding！！！
- 使用RxHttp网络框架链式调用，与Rxjava2相结合，线程智能控制（用户可选retrofit替换 个人比较喜欢rxhttp独特的设计）
- 解决Toast禁用通知权限不能弹出的bug，不分主次线程，可自定义Toast样式
- 全局使用Kotlin，可与Java无缝转换
- 支持新手指引，编程中使用不规范的地方，会引导你正确使用
- 使用插件一键生成Activity/Fragment
- 依赖RxPermission，权限申请更简单
- 使用LiveEventBus，自动销毁，事件传递更加清晰
- 异常捕获机制，将捕获到的异常信息反馈给开发者（Rxjava2异常以及运行时异常）
- 已废弃kotlin-android-extensions插件，完全支持databinding和viewbinding混合使用
- 新增BaseService，扩展了对Service的支持，使用Rxjava以及协程会更方便一些

### 开发准备 ###

必须的项目配置，框架初始化都已添加，所以直接复制本项目更改报名，即可进行开发使用

### 开发指南 ###

使用的第三方框架:

[Jetpack](https://developer.android.google.cn/jetpack "Jetpack")

[Navigation](https://developer.android.google.cn/jetpack/androidx/releases/navigation "Navigation")

[RxJava2](https://github.com/ReactiveX/RxJava "RxJava2")

[ImmersionBar](https://github.com/gyf-dev/ImmersionBar "ImmersionBar")

[AndroidAutoSize](https://github.com/JessYanCoding/AndroidAutoSize)

[okhttp](https://github.com/square/okhttp "okhttp")

[ToastUtils](https://github.com/getActivity/ToastUtils "ToastUtils")

[BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper "BaseRecyclerViewAdapterHelper")

[ALog](https://github.com/Blankj/ALog "ALog")

[RxPermissions](https://github.com/tbruyelle/RxPermissions "RxPermissions")

### 使用插件一键生成Activity/Fragment ###

Android Studio4.0以上不支持自定义模板,可以使用[一键生成TinMvvm组件](https://github.com/932707629/tin-mvvm-template)

TinMvvmTemplate这个文件里放着TinMvv的一键生成工具

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

- 结合BottomNavigationView使用有异常，fragment之间相互重叠，而且每次切换还会重新初始化fragment
  答：Navigation 的适用场景是，不同层级之间 fragment 的跳转和管理，
  同级 fragment 之间请不要使用 Navigation 切换。可以考虑 viewPager 等方式管理。
  
### 已修复问题: ###

- 修复fragment+tablayout使用时会重复创建的问题
- jitpack发布新版本
- 修复HttpHandleCallBack添加生命周期造成的强转异常
- 修复navigation导致fragment重复创建的问题```FixFragmentNavigator```





