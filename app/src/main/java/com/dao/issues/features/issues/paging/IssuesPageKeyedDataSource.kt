package com.dao.issues.features.issues.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.dao.issues.data.IssuesRepositoryInteractor
import com.dao.issues.model.Issue
import com.dao.issues.network.NetworkState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

/**
 * Created in 02/04/19 11:34.
 *
 * @author Diogo Oliveira.
 */
class IssuesPageKeyedDataSource @Inject constructor(
        private val composite: CompositeDisposable,
        private val repository: IssuesRepositoryInteractor) : PageKeyedDataSource<Int, Issue>()
{
    val networkState = MutableLiveData<NetworkState>()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Issue>)
    {
        val consumer: Consumer<Response<List<Issue>>> = Consumer { response ->
            if(response.isSuccessful)
            {
                response.headers().get("Link")?.split(",")?.map { header ->
                    header.substringAfter("page=", "&")[0]
                }.
                        .

                response.headers().get("Link")?.substringAfter("pager=") .matches(Regex("page=(\\d+).*\$"))
                callback.onResult(response.body()!!, 1, 2)
            }
        }

        val error: Consumer<Throwable> = Consumer {
            networkState.postValue(NetworkState.error(it.message))
        }

        val disposable = repository.loadIssues(1)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { networkState.postValue(NetworkState.LOADING) }
                .doOnTerminate { networkState.postValue(NetworkState.LOADED) }
                .subscribe(consumer, error)

        composite.add(disposable)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Issue>)
    {
        val consumer: Consumer<Response<List<Issue>>> = Consumer { response ->
            if(response.isSuccessful)
            {
                callback.onResult(response.body()!!, 1)
            }
        }

        val error: Consumer<Throwable> = Consumer {
            networkState.postValue(NetworkState.error(it.message))
        }

       val disposable = repository.loadIssues(params.requestedLoadSize)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { networkState.postValue(NetworkState.LOADING) }
                .doOnTerminate { networkState.postValue(NetworkState.LOADED) }
               .subscribe(consumer, error)

        composite.add(disposable)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Issue>)
    {
        /* not implemented */
    }
}