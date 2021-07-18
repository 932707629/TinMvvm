package me.soushin.tinmvvm.listener

import android.content.Context


interface ResponseErrorListener {

    fun handleResponseError(context: Context?, t: Throwable?)

    companion object{
        var EMPTY=object :ResponseErrorListener{
            override fun handleResponseError(context: Context?, t: Throwable?) {

            }
        }
    }

}




