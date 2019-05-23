package com.dao.issues.di

import com.dao.issues.KotlinApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


/**
 * Created in 13/05/19 10:14.
 *
 * @author Diogo Oliveira.
 */
@Singleton
@Component(modules =
[
    AndroidSupportInjectionModule::class,
    ActivityBindingModule::class,
    TestAppModule::class
])
interface TestAppComponent : AndroidInjector<KotlinApplication>
{
    @Component.Factory
    abstract class Builder : AndroidInjector.Factory<KotlinApplication>
}