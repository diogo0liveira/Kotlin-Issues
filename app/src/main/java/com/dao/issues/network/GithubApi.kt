package com.dao.issues.network

import com.dao.issues.GithubApi
import com.dao.issues.KeyParameter
import com.dao.issues.model.Comment
import com.dao.issues.model.Issue
import com.dao.issues.model.State
import com.dao.issues.model.User
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Created in 26/03/19 22:35.
 *
 * @author Diogo Oliveira.
 */
interface Github
{
    @GET(GithubApi.ISSUES)
    fun issues(@Query(KeyParameter.PAGE) page: Int,
               @Query(KeyParameter.STATE) state: String = State.ALL.value()): Observable<Response<List<Issue>>>

    @GET
    fun user(@Url url: String): Observable<User>

    @GET
    fun issueComments(@Url url: String): Observable<List<Comment>>
}