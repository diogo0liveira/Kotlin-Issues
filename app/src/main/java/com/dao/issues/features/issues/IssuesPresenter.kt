package com.dao.issues.features.issues

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dao.issues.features.issues.paging.IssuesDataSourceFactory
import com.dao.issues.features.issues.paging.IssuesPageKeyedDataSource
import com.dao.issues.model.Issue
import com.dao.issues.network.NetworkState
import io.reactivex.disposables.CompositeDisposable

/**
 * Created in 26/03/19 21:51.
 *
 * @author Diogo Oliveira.
 */
class IssuesPresenter constructor(private val factory: IssuesDataSourceFactory) : IssuesInteractor.Presenter
{
    private lateinit var view: IssuesInteractor.View
    private val composite: CompositeDisposable = CompositeDisposable()
    private var issues: LiveData<PagedList<Issue>>

    init {
        val config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(75)
                .setPageSize(15)
                .setEnablePlaceholders(false).build()

        issues = LivePagedListBuilder<Int, Issue>(factory, config).build()

    }

    override fun initialize(view: IssuesInteractor.View)
    {
        this.view = view
        this.view.initializeView()
    }

    override fun terminate()
    {
        composite.clear()
    }

    override fun refreshIssues()
    {
        issues.value?.dataSource?.invalidate()
    }

    override fun issuesObserver(): LiveData<PagedList<Issue>> = issues

    override fun loadIssues(): LiveData<NetworkState> =
            Transformations.switchMap<IssuesPageKeyedDataSource, NetworkState>(factory.source) { it.networkState }
}