package com.dao.issues.util

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler


/**
 * Created in 29/03/19 08:31.
 *
 * @author Diogo Oliveira.
 */
class SchedulerProvider(
        private val backgroundScheduler: Scheduler,
        private val foregroundScheduler: Scheduler)
{
    fun <T> applySchedulers(): ObservableTransformer<T, T>
    {
        return object : ObservableTransformer<T, T>
        {
            override fun apply(observable: Observable<T>): ObservableSource<T>
            {
               return observable
                       .subscribeOn(backgroundScheduler)
                       .observeOn(foregroundScheduler)
            }
        }
    }
}