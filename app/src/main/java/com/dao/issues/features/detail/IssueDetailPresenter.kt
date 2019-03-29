package com.dao.issues.features.detail

import com.dao.issues.data.IssuesRepositoryInteractor
import com.dao.issues.model.Issue
import com.dao.issues.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

/**
 * Created in 27/03/19 12:05.
 *
 * @author Diogo Oliveira.
 */
class IssueDetailPresenter constructor(
        private val repository: IssuesRepositoryInteractor,
        private val schedulerProvider: SchedulerProvider) : IssueDetailInteractor.Presenter
{
    private lateinit var view: IssueDetailInteractor.View
    private lateinit var issue: Issue

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

    override fun loadIssue(issue: Issue)
    {
        this.issue = issue
        view.putOnForm(issue)
    }

    override fun showIssuesGithub()
    {
        view.openInBrowser(issue.url)
    }

    override fun loadUserProfile()
    {
        val disposable = repository.loadUser(issue.user.profile)
                .compose(schedulerProvider.applySchedulers())
                .subscribe({ view.loadingUserProfile(it) },
                           { view.executeRequireNetwork { loadUserProfile() } })
        composite.add(disposable)
    }

    override fun loadComments()
    {
        val disposable = repository.loadIssueComments(issue.commentsUrl)
                .compose(schedulerProvider.applySchedulers())
                .doOnSubscribe { view.showLoading() }
                .doOnTerminate { view.hideLoading() }
                .subscribe({ view.loadingComments(it) },
                           { view.executeRequireNetwork { loadComments() } })

        composite.add(disposable)
    }
}
