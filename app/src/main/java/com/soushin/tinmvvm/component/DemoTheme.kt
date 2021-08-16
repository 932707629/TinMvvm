package com.soushin.tinmvvm.component

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.blankj.ALog
import com.bumptech.glide.Glide
import me.soushin.tinmvvm.utils.AppManager

object DemoTheme {

    @JvmStatic
    @BindingAdapter("imageUrl", "error","placeholder")//,requireAll = false
    fun loadImage(view: ImageView, url: String, error: Drawable, placeholder: Drawable) {
        ALog.i("当前页面处于什么状态",AppManager.get().currentActivity?.javaClass?.simpleName);
        Glide.with(view).load(url).error(error).placeholder(placeholder).into(view)
    }


}