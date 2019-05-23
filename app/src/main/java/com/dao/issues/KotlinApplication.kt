package com.dao.issues

import com.dao.issues.di.DaggerAppComponent
import com.dao.issues.util.Logger
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/**
 * Created in 26/03/19 20:46.
 *
 * @author Diogo Oliveira.
 */
class KotlinApplication : DaggerApplication()
{
    lateinit var injector: AndroidInjector<KotlinApplication>

    override fun onCreate()
    {
        injector = DaggerAppComponent.factory().create(this)
        super.onCreate()

        Logger.initialize(BuildConfig.DEBUG, TAG)
        DaggerAppComponent.factory().create(this).inject(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>
    {
        return injector
    }
}