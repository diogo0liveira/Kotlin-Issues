package com.dao.issues.di

import com.dao.issues.util.RxSchedulers
import dagger.Module
import dagger.Provides



/**
 * Created in 28/03/19 23:01.
 *
 * @author Diogo Oliveira.
 */
@Module
class RxModule
{
    @Provides
    fun provideRxSchedulers() = RxSchedulers()
}