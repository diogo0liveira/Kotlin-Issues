package com.dao.issues.data.repository

import com.dao.issues.base.mvp.Repository
import com.dao.issues.data.IssuesDataSourceInteractor
import com.dao.issues.data.remote.IssuesRemoteDataSource
import com.dao.issues.model.Issue
import com.dao.issues.network.ResultError
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created in 03/08/18 12:30.
 *
 * @author Diogo Oliveira.
 */
@Singleton
class IssuesRepository @Inject constructor(remote: IssuesRemoteDataSource):
        Repository<Any, IssuesRemoteDataSource>(Any(), remote), IssuesDataSourceInteractor
{
    override fun load(listener: IssuesDataSourceInteractor.ListIssuesListener)
    {
        remote.load(object : IssuesDataSourceInteractor.ListIssuesListener
        {
            override fun onListSuccess(list: List<Issue>)
            {
                listener.onListSuccess(list)
            }

            override fun onListError(error: ResultError)
            {
                listener.onListError(error)
            }
        })
    }
}