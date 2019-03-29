package com.dao.issues.di

import android.content.Context
import com.dao.issues.TestAppController
import com.dao.issues.di.network.TestNetworkModule
import com.dao.issues.util.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module(includes = [TestNetworkModule::class])
class TestAppModule
{
    @Provides
    @Singleton
    fun provideContext(application: TestAppController): Context
    {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideSchedulerProvider() = SchedulerProvider(Schedulers.io(), AndroidSchedulers.mainThread())
}
