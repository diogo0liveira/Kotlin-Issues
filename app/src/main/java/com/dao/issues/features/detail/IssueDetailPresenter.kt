package com.dao.issues.features.detail

import com.dao.issues.data.repository.IssuesRepository
import com.dao.issues.model.Issue

/**
 * Created in 27/03/19 12:05.
 *
 * @author Diogo Oliveira.
 */
class IssueDetailPresenter constructor(private val repository: IssuesRepository) : IssueDetailInteractor.Presenter
{
    private lateinit var view: IssueDetailInteractor.View

    override fun initialize(view: IssueDetailInteractor.View)
    {
        this.view = view
        this.view.initializeView()
    }

    override fun loadIssue(issue: Issue)
    {
        view.putOnForm(issue)
    }

}