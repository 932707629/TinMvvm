package com.soushin.tinmvvm.app.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import me.soushin.tinmvvm.config.AppComponent
import java.io.InputStream

/**
 *
 * @auther SouShin
 * @time 2020/10/4 13:28
 */
@GlideModule
class OkhttpGlideModule: AppGlideModule() {

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
//        println("是否进行了glide替换okhttp设置${this.javaClass.simpleName}")
        AppComponent.globalConfig?.okHttpClient?.let {
            registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(it))
        }

    }

}