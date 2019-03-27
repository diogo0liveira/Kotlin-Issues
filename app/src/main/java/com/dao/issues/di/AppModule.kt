package com.dao.issues.di

import android.content.Context
import com.dao.issues.KotlinApplication
import com.dao.issues.di.network.NetworkModule
import dagger.Module
import dagger.Provides
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
    fun provideContext(application: KotlinApplication): Context
    {
        return application.applicationContext
    }
}
