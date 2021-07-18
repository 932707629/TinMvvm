package me.soushin.tinmvvm.rxerror.handler

import androidx.arch.core.util.Function
import com.blankj.ALog
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableSource
import java.util.concurrent.TimeUnit

class RetryWithDelay(private val maxRetries:Int,
                     private val retryDelaySecond:Long) :Function<Observable<Throwable>,ObservableSource<*>> {

    private var retryCount=0

    override fun apply(throwableObservable: Observable<Throwable>): ObservableSource<*> {
        return throwableObservable.flatMap {
            if (++retryCount <= maxRetries){
                // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
                ALog.d( "Observable get error, it will try after " + retryDelaySecond
                        + " second, retry count " + retryCount);
                return@flatMap Observable.timer(retryDelaySecond,TimeUnit.SECONDS)
            }
            return@flatMap Observable.error<Throwable>(it)
        }
    }

}