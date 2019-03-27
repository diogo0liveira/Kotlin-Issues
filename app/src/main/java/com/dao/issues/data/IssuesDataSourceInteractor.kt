package com.dao.issues.data

import com.dao.issues.model.Issue
import com.dao.issues.network.ResultError

/**
 * Created in 26/03/19 21:14.
 *
 * @author Diogo Oliveira.
 */
interface IssuesDataSourceInteractor
{
    fun load(listener: ListIssuesListener)

    interface ListIssuesListener
    {
        fun onListSuccess(list: List<Issue>)

        fun onListError(error: ResultError)
    }
}