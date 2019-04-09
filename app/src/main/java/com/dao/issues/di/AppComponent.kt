package com.dao.issues.di

import com.dao.issues.KotlinApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created in 26/03/19 22:06.
 *
 * @author Diogo Oliveira.
 */
@Singleton
@Component(modules =
[
    AndroidSupportInjectionModule::class,
    ActivityBindingModule::class,
    AppModule::class
])
interface AppComponent : AndroidInjector<KotlinApplication>
{
    @Component.Factory
    abstract class Builder : AndroidInjector.Factory<KotlinApplication>
}