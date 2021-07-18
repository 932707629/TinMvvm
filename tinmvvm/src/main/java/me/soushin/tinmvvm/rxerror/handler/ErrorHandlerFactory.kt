package me.soushin.tinmvvm.rxerror.handler

import android.content.Context
import me.soushin.tinmvvm.listener.ResponseErrorListener

class ErrorHandlerFactory(private val mContext: Context,
                          private val mResponseErrorListener: ResponseErrorListener) {

    /**
     * 处理错误
     *
     * @param throwable
     */
    fun handleError(throwable: Throwable?) {
        mResponseErrorListener.handleResponseError(mContext, throwable)
    }

}