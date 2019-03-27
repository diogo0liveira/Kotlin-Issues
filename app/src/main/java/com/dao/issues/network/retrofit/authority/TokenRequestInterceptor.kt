package com.dao.issues.network.retrofit.authority

import com.dao.issues.API.API_KEY
import com.dao.issues.KeyParameter
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Created in 26/03/19 21:21.
 *
 * @author Diogo Oliveira.
 */
class TokenRequestInterceptor : Interceptor
{
    override fun intercept(chain: Interceptor.Chain): Response
    {
        if(!chain.request().url().encodedPath().contains(KeyParameter.API_KEY))
        {
            val request: Request = chain.request()
            val url: HttpUrl = request.url().newBuilder().addQueryParameter(KeyParameter.API_KEY, API_KEY).build()
            val requestBuilder: Request.Builder = request.newBuilder().url(url)
            return chain.proceed(requestBuilder.build())
        }

        return chain.proceed(chain.request())
    }
}