package me.soushin.tinmvvm.rxerror

import android.content.Context
import me.soushin.tinmvvm.listener.ResponseErrorListener
import me.soushin.tinmvvm.rxerror.handler.ErrorHandlerFactory

class RxErrorHandler(builder: Builder) {

    var mHandlerFactory: ErrorHandlerFactory? = null

    init {
        mHandlerFactory = builder.errorHandlerFactory
    }

    companion object{
        fun builder(): Builder {
            return Builder()
        }
    }

    class Builder {
        private var context: Context? = null
        private var mResponseErrorListener: ResponseErrorListener? = null
        var errorHandlerFactory: ErrorHandlerFactory? = null
        fun with(context: Context?): Builder {
            if (context == null) throw NullPointerException("Context cannot be null")
            this.context = context
            return this
        }

        fun responseErrorListener(responseErrorListener: ResponseErrorListener?): Builder {
            if (responseErrorListener == null) throw NullPointerException("responseErrorListener cannot be null")
            mResponseErrorListener = responseErrorListener
            return this
        }

        fun build(): RxErrorHandler {
            checkNotNull(context) { "Context is required" }
            checkNotNull(mResponseErrorListener) { "ResponseErrorListener is required" }
            errorHandlerFactory = ErrorHandlerFactory(context!!, mResponseErrorListener!!)
            return RxErrorHandler(this)
        }
    }


}