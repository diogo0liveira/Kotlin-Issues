package com.dao.issues.features.issues

import com.dao.issues.data.IssuesRepositoryInteractor
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
        val disposable = repository.loadIssues()
                .compose(schedulerProvider.applySchedulers())
                .doOnSubscribe { view.showLoading() }
                .doOnTerminate { view.hideLoading() }
                .subscribe({ view.loadingIssuesList(it)},
                           { view.executeRequireNetwork { loadIssuesList() } })

        composite.add(disposable)
    }
}