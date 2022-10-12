# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#noinspection ShrinkerUnresolvedReference
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}
-optimizationpasses 5

-dontusemixedcaseclassnames
-verbose
-printmapping priguardMapping.txt

-optimizations !code/simplification/artithmetic,!field/*,!class/merging/*
################base_lib###############
-keep class me.soushin.tinmvvm.* {*;}
-dontwarn me.soushin.tinmvvm.**
-keep class java.lang.invoke.* {*;}
-dontwarn java.lang.invoke.**
-keep public class * implements me.soushin.tinmvvm.listener.ConfigModule

 ################xutils###############
-keepattributes Signature,*Annotation*
-keep public class org.xutils.* {
    public protected *;
}
-keep public interface org.xutils.* {
    public protected *;
}
-keepclassmembers class * extends org.xutils.* {
    public protected *;
}
-keepclassmembers @org.xutils.db.annotation.* class * {*;}
-keepclassmembers @org.xutils.http.annotation.* class * {*;}
-keepclassmembers class * {
    #noinspection ShrinkerUnresolvedReference
    @org.xutils.view.annotation.Event <methods>;
}
 ################support###############
 -keep class android.support.* { *; }
 -keep interface android.support.* { *; }
 -dontwarn android.support.**

################butterknife###############
-keep class butterknife.* { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
   @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
 @butterknife.* <methods>;
}
################gson###############
-keepattributes Signature
-keepattributes *Annotation*
#noinspection ShrinkerUnresolvedReference
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.* { *; }
# Application classes that will be serialized/deserialized over Gson
-keep class com.sunloto.shandong.bean.* { *; }
##############liveeventbus
-dontwarn com.jeremyliao.liveeventbus.**
-keep class com.jeremyliao.liveeventbus.* { *; }
-keep class androidx.lifecycle.* { *; }
-keep class androidx.arch.core.* { *; }
##############liveeventbus

################androidEventBus###############
-keep class org.simple.* { *; }
-keep interface org.simple.* { *; }
-keepclassmembers class * {
    #noinspection ShrinkerUnresolvedReference
    @org.simple.eventbus.Subscriber <methods>;
}
-keepattributes *Annotation*
################espresso###############
-keep class android.support.test.espresso.* { *; }
-keep interface android.support.test.espresso.* { *; }

################annotation###############
-keep class android.support.annotation.* { *; }
-keep interface android.support.annotation.* { *; }

# Marshmallow removed Notification.setLatestEventInfo()
-dontwarn android.app.Notification

################immersionbar###############
-keep class com.gyf.immersionbar.* {*;}
-dontwarn com.gyf.immersionbar.**

################basepopup###############
-dontwarn razerdp.basepopup.**
-keep class razerdp.basepopup.*{*;}


