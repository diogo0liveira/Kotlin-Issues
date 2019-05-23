package com.dao.issues.di.network

import com.dao.issues.TestGithubApi
import com.dao.issues.network.Github
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Created in 23/05/19 15:57.
 *
 * @author Diogo Oliveira.
 */
@Module
class TestNetworkModule
{
    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient =
            OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient, moshi: Moshi): Retrofit =
            Retrofit.Builder()
                    .client(httpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .baseUrl(TestGithubApi.URL).build()

    @Provides
    @Singleton
    fun provideNetworkService(retrofit: Retrofit): Github
    {
        return retrofit.create(Github::class.java)
    }
}