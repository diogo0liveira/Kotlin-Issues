package com.dao.issues.features.issues.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.dao.issues.data.IssuesRepositoryInteractor
import com.dao.issues.model.Issue
import com.dao.issues.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created in 02/04/19 11:30.
 *
 * @author Diogo Oliveira.
 */
class IssuesDataSourceFactory @Inject constructor(
        private val composite: CompositeDisposable,
        private val schedulerProvider: SchedulerProvider,
        private val repository: IssuesRepositoryInteractor): DataSource.Factory<Int, Issue>()
{
    val source = MutableLiveData<IssuesPageKeyedDataSource>()

    override fun create(): DataSource<Int, Issue>
    {
        val issuesDataSource = IssuesPageKeyedDataSource(composite, schedulerProvider, repository)
        this.source.postValue(issuesDataSource)
        return issuesDataSource
    }
}