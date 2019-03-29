package com.dao.issues.di

import com.dao.issues.TestAppController
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules =
[
    AndroidSupportInjectionModule::class,
    ActivityBindingModule::class,
    TestAppModule::class
])
interface TestAppComponent : AndroidInjector<TestAppController>
{
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<TestAppController>()
}