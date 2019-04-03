package com.dao.issues.features.issues

import com.dao.issues.data.repository.IssuesRepository
import com.dao.issues.di.annotations.ActivityScoped
import com.dao.issues.features.issues.paging.IssuesDataSourceFactory
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

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
    fun provideCompositeDisposable() = CompositeDisposable()

    @Provides
    @ActivityScoped
    fun provideIssuesPresenter(factory: IssuesDataSourceFactory) = IssuesPresenter(factory)

    @Provides
    @ActivityScoped
    fun provideIssuesDataSourceFactory(composite: CompositeDisposable, repository: IssuesRepository) =
            IssuesDataSourceFactory(composite, repository)
}