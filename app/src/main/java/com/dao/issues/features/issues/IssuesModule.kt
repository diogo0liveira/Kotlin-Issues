package com.dao.issues.features.issues

import com.dao.issues.data.repository.IssuesRepository
import com.dao.issues.di.annotations.ActivityScoped
import com.dao.issues.util.SchedulerProvider
import dagger.Module
import dagger.Provides

/**
 * Created in 26/03/19 22:44.
 *
 * @author Diogo Oliveira.
 */
@Module
class IssuesModule
{
    @Provides
    @ActivityScoped
    fun provideIssuesPresenter(repository: IssuesRepository, schedulerProvider: SchedulerProvider): IssuesInteractor.Presenter
    {
        return IssuesPresenter(repository, schedulerProvider)
    }
}