package com.dao.issues.features.detail

import com.dao.issues.data.repository.IssuesRepository
import com.dao.issues.di.annotations.ActivityScoped
import com.dao.issues.util.SchedulerProvider
import dagger.Module
import dagger.Provides

/**
 * Created in 27/03/19 12:05.
 *
 * @author Diogo Oliveira.
 */
@Module
class IssueDetailModule
{
    @Provides
    @ActivityScoped
    fun provideIssueDetailPresenter(repository: IssuesRepository, schedulerProvider: SchedulerProvider): IssueDetailInteractor.Presenter
    {
        return IssueDetailPresenter(repository, schedulerProvider)
    }
}