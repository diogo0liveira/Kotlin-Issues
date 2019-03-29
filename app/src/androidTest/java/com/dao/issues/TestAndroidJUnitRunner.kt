package com.dao.issues

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class TestAndroidJUnitRunner : AndroidJUnitRunner()
{
    @Throws(Exception::class)
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application
    {
        return super.newApplication(cl, TestAppController::class.java.name, context)
    }
}