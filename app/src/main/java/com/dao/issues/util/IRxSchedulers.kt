package com.dao.issues.util

import io.reactivex.Scheduler



/**
 * Created in 28/03/19 23:02.
 *
 * @author Diogo Oliveira.
 */
interface IRxSchedulers
{
    fun io(): Scheduler

    fun androidThread(): Scheduler
}