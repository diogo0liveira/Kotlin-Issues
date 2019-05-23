package com.dao.issues.rx2idlerktx

import androidx.test.espresso.IdlingRegistry
import io.reactivex.Scheduler
import io.reactivex.functions.Function
import java.util.concurrent.Callable

object Rx2IdlerKtx {

    fun create(name: String): Function<Callable<Scheduler>, Scheduler> {
        return Function { delegate ->
            val scheduler = DelegatingIdlingResourceScheduler(delegate.call(), name)
            IdlingRegistry.getInstance().register(scheduler)
            scheduler
        }
    }
}