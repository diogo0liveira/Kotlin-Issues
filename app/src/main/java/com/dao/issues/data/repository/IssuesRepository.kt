package com.dao.issues.data.repository

import com.dao.issues.base.mvp.Repository
import com.dao.issues.data.IssuesRepositoryInteractor
import com.dao.issues.data.remote.IssuesRemoteRepository
import com.dao.issues.model.Comment
import com.dao.issues.model.Issue
import com.dao.issues.model.User
import io.reactivex.Observable
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created in 03/08/18 12:30.
 *
 * @author Diogo Oliveira.
 */
@Singleton
class IssuesRepository @Inject constructor(remote: IssuesRemoteRepository):
        Repository<Any, IssuesRemoteRepository>(Any(), remote), IssuesRepositoryInteractor
{
    override fun loadUser(url: String): Observable<User> = remote.loadUser(url)

    override fun loadIssues(page: Int): Observable<Response<List<Issue>>> = remote.loadIssues(page)

    override fun loadIssueComments(url: String): Observable<List<Comment>> = remote.loadIssueComments(url)
}