package com.dao.issues.features.issues

import com.dao.issues.data.repository.IssuesRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created in 26/03/19 21:51.
 *
 * @author Diogo Oliveira.
 */
class IssuesPresenter constructor(private val repository: IssuesRepository) : IssuesInteractor.Presenter
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
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { view.showLoading() }
                .doOnTerminate { view.hideLoading() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { view.loadingIssuesList(it) }

        composite.add(disposable)
    }
}