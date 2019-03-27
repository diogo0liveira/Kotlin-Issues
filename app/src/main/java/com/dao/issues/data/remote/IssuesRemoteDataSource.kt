package com.dao.issues.data.remote

import com.dao.issues.R
import com.dao.issues.data.IssuesDataSourceInteractor
import com.dao.issues.model.Issue
import com.dao.issues.network.ResultError
import com.dao.issues.network.Status
import com.dao.issues.network.retrofit.GithubApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created in 26/03/19 21:14.
 *
 * @author Diogo Oliveira.
 */
@Singleton
class IssuesRemoteDataSource @Inject constructor(private val service: GithubApi) : IssuesDataSourceInteractor
{
    override fun load(listener: IssuesDataSourceInteractor.ListIssuesListener)
    {
        val call: Call<List<Issue>> = service.issues()

        call.enqueue(object : Callback<List<Issue>>
        {
            override fun onResponse(call: Call<List<Issue>>, response: Response<List<Issue>>)
            {
                if(response.isSuccessful)
                {
                    listener.onListSuccess(response.body()!!)
                }
                else
                {
                    listener.onListError(ResultError.parse(response))
                }
            }

            override fun onFailure(call: Call<List<Issue>>, throwable: Throwable)
            {
                throwable.printStackTrace()
                listener.onListError(ResultError(Status.SERVICE_UNAVAILABLE, R.string.app_internal_server_unavailable))
            }
        })
    }
}