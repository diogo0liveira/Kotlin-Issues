package com.dao.issues.data

import com.dao.issues.model.Comment
import com.dao.issues.model.Issue
import com.dao.issues.model.User
import io.reactivex.Observable

/**
 * Created in 26/03/19 21:14.
 *
 * @author Diogo Oliveira.
 */
interface IssuesDataSourceInteractor
{
    fun loadUser(url: String): Observable<User>

    fun loadIssues(): Observable<List<Issue>>

    fun loadIssueComments(url: String): Observable<List<Comment>>
}