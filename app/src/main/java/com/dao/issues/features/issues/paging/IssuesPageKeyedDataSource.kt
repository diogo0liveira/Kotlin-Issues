package com.dao.issues.features.issues.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.dao.issues.R
import com.dao.issues.data.IssuesRepositoryInteractor
import com.dao.issues.model.Issue
import com.dao.issues.network.NetworkState
import com.dao.issues.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import okhttp3.Headers
import retrofit2.Response
import javax.inject.Inject

/**
 * Created in 02/04/19 11:34.
 *
 * @author Diogo Oliveira.
 */
class IssuesPageKeyedDataSource @Inject constructor(
        private val composite: CompositeDisposable,
        private val schedulerProvider: SchedulerProvider,
        private val repository: IssuesRepositoryInteractor) : PageKeyedDataSource<Int, Issue>()
{
    val networkState = MutableLiveData<NetworkState>()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Issue>)
    {
        val consumer: Consumer<Response<List<Issue>>> = Consumer { response ->
            if(response.isSuccessful)
            {
                response.body()?.let { search ->
                    val limits = pagination(response.headers())
                    callback.onResult(search, 1, limits["next"])
                }

                networkState.postValue(NetworkState.SUCCESS)
            }
            else
            {
                networkState.postValue(NetworkState.error(
                        R.string.app_internal_server_unavailable) { loadInitial(params, callback) })
            }
        }

        val error: Consumer<Throwable> = Consumer {
            networkState.postValue(NetworkState.error(it.message) { loadInitial(params, callback) })
        }

        val disposable = repository.loadIssues(1)
                .doOnSubscribe { networkState.postValue(NetworkState.RUNNING) }
                .compose(schedulerProvider.applySchedulers())
                .subscribe(consumer, error)

        composite.add(disposable)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Issue>)
    {
        val consumer: Consumer<Response<List<Issue>>> = Consumer { response ->
            if(response.isSuccessful)
            {
                response.body()?.let { search ->
                    val limits = pagination(response.headers())
                    callback.onResult(search, limits["next"])
                }

                networkState.postValue(NetworkState.SUCCESS)
            }
            else
            {
                networkState.postValue(NetworkState.error(
                        R.string.app_internal_server_unavailable) { loadAfter(params, callback) })
            }
        }

        val error: Consumer<Throwable> = Consumer {
            networkState.postValue(NetworkState.error(it.message) { loadAfter(params, callback) })
        }

       val disposable = repository.loadIssues(params.key)
               .doOnSubscribe { networkState.postValue(NetworkState.RUNNING) }
               .compose(schedulerProvider.applySchedulers())
               .subscribe(consumer, error)

        composite.add(disposable)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Issue>)
    {
        /* not implemented */
    }

    private fun pagination(headers: Headers): Map<String, Int?>
    {
        var limits = mapOf("prev" to 1, "next" to null)

        headers.get("Link")?.let { header ->
           limits = header
                    .split(",")
                    .map { (it.substringAfter("rel=").removePrefix("\"").removeSuffix("\"") to
                            it.substringAfter("?page=").substringBefore("&").toInt()) }.toMap()
        }

        return limits
    }
}