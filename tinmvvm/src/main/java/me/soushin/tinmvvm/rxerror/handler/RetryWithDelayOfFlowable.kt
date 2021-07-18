package me.soushin.tinmvvm.rxerror.handler

import androidx.arch.core.util.Function
import com.blankj.ALog
import io.reactivex.rxjava3.core.Flowable
import org.reactivestreams.Publisher
import java.util.concurrent.TimeUnit

class RetryWithDelayOfFlowable(private val maxRetries:Int,
                               private val retryDelaySecond:Long) :
    Function<Flowable<Throwable>, Publisher<*>> {

    private var retryCount=0

    override fun apply(throwableObservable: Flowable<Throwable>): Publisher<*> {
        return throwableObservable.flatMap {
            if (++retryCount <= maxRetries){
                // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
                ALog.d( "Observable get error, it will try after " + retryDelaySecond
                        + " second, retry count " + retryCount);
                return@flatMap Flowable.timer(retryDelaySecond, TimeUnit.SECONDS)
            }
            return@flatMap Flowable.error<Throwable>(it)
        }
    }

}