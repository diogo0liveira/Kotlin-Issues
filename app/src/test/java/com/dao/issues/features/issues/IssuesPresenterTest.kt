package com.dao.issues.features.issues

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.*
import com.dao.issues.data.IssuesRepositoryInteractor
import com.dao.issues.features.issues.paging.IssuesDataSourceFactory
import com.dao.issues.model.Issue
import com.dao.issues.util.SchedulerProvider
import com.dao.issues.util.mock
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import retrofit2.Response


/**
 * Created in 28/03/19 22:04.
 *
 * @author Diogo Oliveira.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class IssuesPresenterTest
{
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var view: IssuesInteractor.View
    @Mock
    private lateinit var repository: IssuesRepositoryInteractor

    private lateinit var factory: IssuesDataSourceFactory
    private lateinit var presenter: IssuesPresenter

    @Before
    fun setUp()
    {
        MockitoAnnotations.initMocks(this)

        val scheduler = SchedulerProvider(Schedulers.trampoline(), Schedulers.trampoline())
        factory = IssuesDataSourceFactory(CompositeDisposable(), scheduler, repository)

        presenter = IssuesPresenter(factory)
        presenter.initialize(view)
    }

    @Test
    fun initialize()
    {
        verify(view).initializeView()
    }


    @Test
    fun `load issues`()
    {
        `when`(repository.loadIssues(1)).thenReturn(Observable.just<Response<List<Issue>>>(mock()))

        presenter.issuesObserver().observeOnce {
            assertThat(0, `is`(it.loadedCount))
        }
    }
}

class OneTimeObserver<T>(private val handler: (T) -> Unit) : Observer<T>, LifecycleOwner
{
    private val lifecycle = LifecycleRegistry(this)

    init
    {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override fun getLifecycle(): Lifecycle = lifecycle

    override fun onChanged(t: T)
    {
        handler(t)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }
}

fun <T> LiveData<T>.observeOnce(onChangeHandler: (T) -> Unit)
{
    val observer = OneTimeObserver(handler = onChangeHandler)
    observe(observer, observer)
}