package com.dao.issues.di.network

import android.content.Context
import com.dao.issues.BuildConfig
import com.dao.issues.GithubApi
import com.dao.issues.network.Github
import com.dao.issues.network.interceptor.GithubVersionInterceptor
import com.dao.issues.network.interceptor.TokenRequestInterceptor
import com.dao.issues.util.json.GsonHelper
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created in 26/03/19 22:02.
 *
 * @author Diogo Oliveira.
 */
@Module
class NetworkModule
{
    @Provides
    @Singleton
    fun provideCache(context: Context): Cache = Cache(context.cacheDir, 5 * 1024 * 1024)

    @Provides
    @Singleton
    fun provideInterceptor(): Interceptor =
            HttpLoggingInterceptor().apply {
                level = if(BuildConfig.DEBUG) Level.BODY else Level.NONE
            }

    @Provides
    @Singleton
    fun provideHttpClient(interceptor: Interceptor, cache: Cache): OkHttpClient =
            OkHttpClient
                    .Builder()
                    .addInterceptor(GithubVersionInterceptor())
                    .addInterceptor(TokenRequestInterceptor())
                    .addNetworkInterceptor(interceptor)
                    .cache(cache)
                    .build()
    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit =
            Retrofit.Builder()
                .client(httpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonHelper.build())).baseUrl(GithubApi.URL).build()


    @Provides
    @Singleton
    fun provideNetworkService(retrofit: Retrofit): Github
    {
        return retrofit.create(Github::class.java)
    }
}