package com.dao.issues.di

import com.dao.issues.di.annotations.ActivityScoped
import com.dao.issues.features.detail.IssueDetailActivity
import com.dao.issues.features.detail.IssueDetailModule
import com.dao.issues.features.issues.IssuesActivity
import com.dao.issues.features.issues.IssuesModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created in 26/03/19 22:05.
 *
 * @author Diogo Oliveira.
 */
@Module
abstract class ActivityBindingModule
{
    @ActivityScoped
    @ContributesAndroidInjector(modules = [IssuesModule::class])
    abstract fun bindIssuesActivity(): IssuesActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [IssueDetailModule::class])
    abstract fun bindIssueDetailActivity(): IssueDetailActivity
}