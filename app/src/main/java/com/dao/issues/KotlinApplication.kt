package com.dao.issues

import android.app.Activity
import android.app.Application
import com.dao.issues.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Created in 26/03/19 20:46.
 *
 * @author Diogo Oliveira.
 */
open class KotlinApplication : Application(), HasActivityInjector
{
    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate()
    {
        super.onCreate()
        Boolean
        DaggerAppComponent.builder().create(this).inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector
}