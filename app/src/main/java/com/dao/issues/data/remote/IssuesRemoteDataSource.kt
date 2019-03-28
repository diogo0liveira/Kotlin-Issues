package com.dao.issues.data.remote

import com.dao.issues.data.IssuesDataSourceInteractor
import com.dao.issues.model.Comment
import com.dao.issues.model.Issue
import com.dao.issues.network.retrofit.GithubApi
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created in 26/03/19 21:14.
 *
 * @author Diogo Oliveira.
 */
@Singleton
class IssuesRemoteDataSource @Inject constructor(private val service: GithubApi) : IssuesDataSourceInteractor
{
    override fun loadIssues(): Observable<List<Issue>> = service.issues()

    override fun loadIssueComments(url: String): Observable<List<Comment>> = service.issueComments(url)
}