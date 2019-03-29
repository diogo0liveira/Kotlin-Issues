package com.dao.issues.util

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created in 28/03/19 23:03.
 *
 * @author Diogo Oliveira.
 */
class RxSchedulers: IRxSchedulers
{
    override fun io() = Schedulers.io()

    override fun androidThread() = AndroidSchedulers.mainThread()!!
}