package com.dao.issues.features.issues

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dao.issues.data.IssuesRepositoryInteractor
import com.dao.issues.features.issues.paging.IssuesDataSourceFactory
import com.dao.issues.features.issues.paging.IssuesPageKeyedDataSource
import com.dao.issues.model.Issue
import com.dao.issues.network.NetworkState
import com.dao.issues.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

/**
 * Created in 26/03/19 21:51.
 *
 * @author Diogo Oliveira.
 */
class IssuesPresenter constructor(
        private val repository: IssuesRepositoryInteractor,
        private val schedulerProvider: SchedulerProvider) : IssuesInteractor.Presenter
{
    private lateinit var view: IssuesInteractor.View
    private val composite: CompositeDisposable = CompositeDisposable()

    private var issues: LiveData<PagedList<Issue>>
    private val factory: IssuesDataSourceFactory

    init {
        factory = IssuesDataSourceFactory(composite, repository)

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

    override fun loadIssuesList()
    {
//        val disposable = repository.loadIssues()
//                .compose(schedulerProvider.applySchedulers())
//                .doOnSubscribe { view.showLoading() }
//                .doOnTerminate { view.hideLoading() }
//                .subscribe({ view.loadingIssuesList(it)},
//                           { view.executeRequireNetwork { loadIssuesList() } })
//
//        composite.add(disposable)

    }

    override fun issuesObserver(): LiveData<PagedList<Issue>> = issues

    override fun loadIssues(): LiveData<NetworkState> =
            Transformations.switchMap<IssuesPageKeyedDataSource, NetworkState>(factory.source) { it.networkState }

    override fun getNetworkState(): LiveData<NetworkState> =
            Transformations.switchMap<IssuesPageKeyedDataSource, NetworkState>(factory.source) { it.networkState }
}