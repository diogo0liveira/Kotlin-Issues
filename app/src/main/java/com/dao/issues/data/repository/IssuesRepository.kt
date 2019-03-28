package com.dao.issues.data.repository

import com.dao.issues.base.mvp.Repository
import com.dao.issues.data.IssuesDataSourceInteractor
import com.dao.issues.data.remote.IssuesRemoteDataSource
import com.dao.issues.model.Comment
import com.dao.issues.model.Issue
import com.dao.issues.model.User
import io.reactivex.Observable
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
    override fun loadUser(url: String): Observable<User> = remote.loadUser(url)

    override fun loadIssues(): Observable<List<Issue>> = remote.loadIssues()

    override fun loadIssueComments(url: String): Observable<List<Comment>> = remote.loadIssueComments(url)
}