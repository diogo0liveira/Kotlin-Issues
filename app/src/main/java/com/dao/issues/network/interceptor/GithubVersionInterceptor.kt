package com.dao.issues.network.interceptor

import com.dao.issues.GithubApi
import com.dao.issues.KeyHeader
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Created in 01/04/19 11:55.
 *
 * @author Diogo Oliveira.
 */
class GithubVersionInterceptor: Interceptor
{
    override fun intercept(chain: Interceptor.Chain): Response
    {
        if(chain.request().url.toString().startsWith(GithubApi.URL))
        {
            val original: Request = chain.request()
            val request = original.newBuilder().header(KeyHeader.ACCEPT, GithubApi.VERSION).build()
            return chain.proceed(request)
        }

        return chain.proceed(chain.request())
    }
}