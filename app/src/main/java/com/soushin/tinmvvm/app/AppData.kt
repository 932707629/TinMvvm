package com.soushin.tinmvvm.app

import com.soushin.tinmvvm.R
import com.tencent.mmkv.MMKV

/**
 * 应用内数据缓存
 */
class AppData private constructor(){

    companion object {
        private val instance: AppData by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            AppData()
        }
        @JvmStatic
        fun get() : AppData {
            return instance
        }
    }

    private val mmkv by lazy { MMKV.defaultMMKV()!! }

    private var defaultTransitionsAnim:Int?=null
    private var defaultTheme:Int?=null

    fun queryAnim():Int?{
        defaultTransitionsAnim = mmkv.decodeInt(GlobalConstants.tag_transitions_anim,0)
        if (defaultTransitionsAnim == 0) return null
        return defaultTransitionsAnim!!
    }

    fun saveNavOptions(transition: Int){
        if (mmkv.encode(GlobalConstants.tag_transitions_anim,transition)){
            defaultTransitionsAnim = transition
        }
    }

    fun queryNavOptions():IntArray?{
        return when(queryAnim()){
            R.id.single_menu_alpha->{
                intArrayOf(R.anim.alpha_in,R.anim.alpha_out)
            }
            R.id.single_menu_slide_left->{
                intArrayOf(R.anim.slide_in_left,R.anim.slide_out_left)
            }
            R.id.single_menu_slide_right->{
                intArrayOf(R.anim.slide_in_right,R.anim.slide_out_right)
            }
            R.id.single_menu_window_bottom->{
                intArrayOf(R.anim.window_bottom_in,R.anim.window_bottom_out)
            }
            R.id.single_menu_window_ios->{
                intArrayOf(R.anim.window_ios_in,R.anim.window_ios_out)
            }
            R.id.single_menu_window_left->{
                intArrayOf(R.anim.window_left_in,R.anim.window_left_out)
            }
            R.id.single_menu_window_right->{
                intArrayOf(R.anim.window_right_in,R.anim.window_right_out)
            }
            R.id.single_menu_window_scale->{
                intArrayOf(R.anim.window_scale_in,R.anim.window_scale_out)
            }
            R.id.single_menu_window_top->{
                intArrayOf(R.anim.window_top_in,R.anim.window_top_out)
            }
            else ->{ null }
        }
    }

//    private fun buildNavOptions(enter:Int,exit:Int):NavOptions{
//        return NavOptions.Builder().setEnterAnim(enter).setExitAnim(exit).setPopEnterAnim(enter).setPopExitAnim(exit).build()
//    }

    fun queryComponent():Any?{
        if (defaultTheme == null){
            defaultTheme = mmkv.decodeInt(GlobalConstants.tag_component_theme,0)
        }
        return null
    }

    fun saveComponentTheme(theme:Int){
        if (mmkv.encode(GlobalConstants.tag_component_theme,theme)){
            defaultTheme=theme
        }
    }




}