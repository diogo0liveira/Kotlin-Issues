package com.dao.issues.features.issues

import com.dao.issues.data.IssuesRepositoryInteractor
import com.dao.issues.model.Issue
import com.dao.issues.util.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


/**
 * Created in 28/03/19 22:04.
 *
 * @author Diogo Oliveira.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class IssuesPresenterTest
{
    @Mock
    private lateinit var view: IssuesInteractor.View
    @Mock
    private lateinit var repository: IssuesRepositoryInteractor

    private val schedulerProvider = SchedulerProvider(Schedulers.trampoline(), Schedulers.trampoline())
    private lateinit var presenter: IssuesPresenter

    @Before
    fun setUp()
    {
        MockitoAnnotations.initMocks(this)
        presenter = IssuesPresenter(repository, schedulerProvider)
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
        val issues = listOf<Issue>()
        `when`(repository.loadIssues()).thenReturn(Observable.just<List<Issue>>(issues))

        presenter.loadIssuesList()
        verify(view).loadingIssuesList(issues)
    }
}