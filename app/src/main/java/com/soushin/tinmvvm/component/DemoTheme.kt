package com.soushin.tinmvvm.component

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.blankj.ALog
import com.bumptech.glide.Glide

object DemoTheme {

    @JvmStatic
    @BindingAdapter("imageUrl", "error",requireAll = false)//
    fun loadImage(view: ImageView, url: String?, error: Drawable?) {
        ALog.i("loadImage",view.javaClass.simpleName,url,error?.javaClass?.simpleName)
        Glide.with(view).load(url).error(error).into(view)
    }


}