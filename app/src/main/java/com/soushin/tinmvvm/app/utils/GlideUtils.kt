package com.soushin.tinmvvm.app.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.soushin.tinmvvm.R

/**
 * 图片加载
 * @author SouShin
 * created at 2022/11/25 11:41
 */
class GlideUtils private  constructor(){

    companion object {
        private val instance: GlideUtils by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            GlideUtils()
        }
        @JvmStatic
        fun get() : GlideUtils {
            return instance
        }
    }

    /**
     * 图片加载
     */
    fun loadImages(img:ImageView?, path:Any?, error: Int= R.mipmap.ic_launcher, placeholder: Int= R.mipmap.ic_launcher){
        img?.let {
            Glide.with(it.context).load(path).error(error).placeholder(placeholder).into(img)
        }
    }


    /**
     * 图片加载
     */
    fun loadImages(img:ImageView?, path:Any?, error: Drawable?, placeholder: Drawable?){
        img?.let {
            Glide.with(it.context).load(path).error(error).placeholder(placeholder).into(img)
        }
    }



}