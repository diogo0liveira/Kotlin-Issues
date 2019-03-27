package com.dao.issues.network.retrofit

import com.dao.issues.API
import com.dao.issues.model.Issue
import com.dao.issues.util.network.ContentType
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

/**
 * Created in 26/03/19 22:35.
 *
 * @author Diogo Oliveira.
 */
interface GithubApi
{
    @GET(API.URL_ISSUES)
    @Headers(ContentType.APPLICATION_JSON)
    fun issues(): Call<List<Issue>>
}