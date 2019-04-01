package com.dao.issues.data.remote

import com.dao.issues.data.IssuesRepositoryInteractor
import com.dao.issues.model.Comment
import com.dao.issues.model.Issue
import com.dao.issues.model.User
import com.dao.issues.network.Github
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created in 26/03/19 21:14.
 *
 * @author Diogo Oliveira.
 */
@Singleton
class IssuesRemoteRepository @Inject constructor(private val service: Github) : IssuesRepositoryInteractor
{
    override fun loadUser(url: String): Observable<User> = service.user(url)

    override fun loadIssues(): Observable<List<Issue>> = service.issues()

    override fun loadIssueComments(url: String): Observable<List<Comment>>
    {
        return service.issueComments(url)
                .flatMap { Observable.fromIterable(it) }
                .flatMap { service.user(it.user.profile).flatMap { user ->
                        Observable.just(Comment(it.created, it.author, it.body, user)) }
                }.toList().toObservable()
    }
}
