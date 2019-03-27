package com.dao.issues.features.issues

import android.util.Log
import android.widget.Toast
import com.dao.issues.TAG
import com.dao.issues.data.IssuesDataSourceInteractor
import com.dao.issues.data.repository.IssuesRepository
import com.dao.issues.model.Issue
import com.dao.issues.network.ResultError

/**
 * Created in 26/03/19 21:51.
 *
 * @author Diogo Oliveira.
 */
class IssuesPresenter constructor(private val repository: IssuesRepository) : IssuesInteractor.Presenter
{
    private lateinit var view: IssuesInteractor.View

    override fun initialize(view: IssuesInteractor.View)
    {
        this.view = view
        this.view.initializeView()
    }

    override fun loadIssuesList()
    {
        repository.load(object : IssuesDataSourceInteractor.ListIssuesListener
        {
            override fun onListSuccess(list: List<Issue>)
            {
                view.loadingIssuesList(list)
            }

            override fun onListError(error: ResultError)
            {
                view.toast(error.messageRes, Toast.LENGTH_LONG)
                Log.e(TAG, "erro ao carregar issues")
            }

        })
    }
}