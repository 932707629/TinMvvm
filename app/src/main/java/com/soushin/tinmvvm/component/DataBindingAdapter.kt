package com.soushin.tinmvvm.component

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.soushin.tinmvvm.app.utils.GlideUtils

object DataBindingAdapter {

    @JvmStatic
    @BindingAdapter("image", "error","placeholder",requireAll = false)//,requireAll = false
    fun loadImage(view: ImageView, image: Any, error: Drawable?, placeholder: Drawable?) {
//        ALog.i("当前页面处于什么状态", AppManager.get().currentActivity?.javaClass?.simpleName);
        GlideUtils.get().loadImages(view,image,error=error,placeholder=placeholder)
    }


}