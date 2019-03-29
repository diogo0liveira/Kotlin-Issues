package com.dao.issues

import com.dao.issues.di.DaggerTestAppComponent

class TestAppController : KotlinApplication()
{
    override fun onCreate()
    {
        super.onCreate()
        DaggerTestAppComponent.builder().create(this).inject(this)
    }
}