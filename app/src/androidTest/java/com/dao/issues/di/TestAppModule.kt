package com.dao.issues.di

import android.content.Context
import com.dao.issues.KotlinApplication
import com.dao.issues.di.network.TestNetworkModule
import com.dao.issues.util.SchedulerProvider
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Singleton

/**
 * Created in 23/05/19 16:05.
 *
 * @author Diogo Oliveira.
 */
@Module(includes = [TestNetworkModule::class])
class TestAppModule
{
    @Provides
    @Singleton
    fun provideContext(application: KotlinApplication): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideSchedulerProvider() = SchedulerProvider(Schedulers.io(), AndroidSchedulers.mainThread())

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
            .add(Date::class.java, Rfc3339DateJsonAdapter())
            .add(KotlinJsonAdapterFactory()).build()
}