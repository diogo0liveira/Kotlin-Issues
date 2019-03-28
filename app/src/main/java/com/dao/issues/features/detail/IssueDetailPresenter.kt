package com.dao.issues.features.detail

import com.dao.issues.data.repository.IssuesRepository
import com.dao.issues.model.Issue
import com.dao.issues.model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created in 27/03/19 12:05.
 *
 * @author Diogo Oliveira.
 */
class IssueDetailPresenter constructor(private val repository: IssuesRepository) : IssueDetailInteractor.Presenter
{
    private lateinit var view: IssueDetailInteractor.View
    private val composite: CompositeDisposable = CompositeDisposable()

    override fun initialize(view: IssueDetailInteractor.View)
    {
        this.view = view
        this.view.initializeView()
    }

    override fun terminate()
    {
        composite.clear()
    }

    override fun loadUserProfile(user: User)
    {
        val disposable = repository.loadUser(user.profile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { view.loadingUserProfile(it) }
        composite.add(disposable)
    }

    override fun loadIssue(issue: Issue)
    {
        view.putOnForm(issue)
    }

    override fun loadComments(issue: Issue)
    {
        val disposable = repository.loadIssueComments(issue.commentsUrl)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { view.showLoading() }
                .doOnTerminate { view.hideLoading() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { view.loadingComments(it) }

        composite.add(disposable)
    }
}