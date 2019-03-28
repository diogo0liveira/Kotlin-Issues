package com.dao.issues.network

import com.dao.issues.API
import com.dao.issues.model.Comment
import com.dao.issues.model.Issue
import com.dao.issues.model.User
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Url

/**
 * Created in 26/03/19 22:35.
 *
 * @author Diogo Oliveira.
 */
interface GithubApi
{
    @GET(API.URL_ISSUES)
    @Headers(ContentType.APPLICATION_JSON)
    fun issues(): Observable<List<Issue>>

    @GET
    @Headers(ContentType.APPLICATION_JSON)
    fun user(@Url url: String): Observable<User>

    @GET
    @Headers(ContentType.APPLICATION_JSON)
    fun issueComments(@Url url: String): Observable<List<Comment>>
}