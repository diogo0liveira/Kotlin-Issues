package com.dao.issues.di

import android.content.Context
import com.dao.issues.KotlinApplication
import com.dao.issues.di.network.NetworkModule
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
 * Created in 26/03/19 22:08.
 *
 * @author Diogo Oliveira.
 */
@Module(includes = [NetworkModule::class])
class AppModule
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
