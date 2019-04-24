package com.dao.issues.data.remote

import com.dao.issues.data.IssuesRepositoryInteractor
import com.dao.issues.model.Comment
import com.dao.issues.model.Issue
import com.dao.issues.model.User
import com.dao.issues.network.Github
import io.reactivex.Observable
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created in 26/03/19 21:14.
 *
 * @author Diogo Oliveira.
 */
@Singleton
class IssuesRemoteDataSource @Inject constructor(private val service: Github) : IssuesRepositoryInteractor
{
    override fun loadUser(url: String): Observable<User> = service.user(url)

    override fun loadIssues(page: Int): Observable<Response<List<Issue>>> = service.issues(page)

    override fun loadIssueComments(url: String): Observable<List<Comment>>
    {
        return service.issueComments(url)
                .flatMap { Observable.fromIterable(it) }
                .flatMap { comment -> service.user(comment.user.profile)
                .flatMap { user -> Observable.just(Comment(comment.created, comment.author, comment.body, user)) }
                }.toList().toObservable()
    }
}
