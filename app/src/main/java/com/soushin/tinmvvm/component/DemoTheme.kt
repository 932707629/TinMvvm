package com.soushin.tinmvvm.component

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object DemoTheme {

    /*@BindingAdapter("android:text")
    @JvmStatic
    fun setText(view: TextView, value:String) {
        view.text = value
    }

    @BindingAdapter("android:textColor")
    @JvmStatic
    fun setTextColor(view: TextView, value:Int){
        view.setTextColor(value)
    }*/
    @BindingAdapter("imageUrl", "error")
    fun loadImage(view: ImageView, url: String, error: Drawable) {
        Glide.with(view).load(url).error(error).into(view)
    }


}