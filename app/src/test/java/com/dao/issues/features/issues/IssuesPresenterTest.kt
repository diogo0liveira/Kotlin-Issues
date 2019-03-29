package com.dao.issues.features.issues

import com.dao.issues.data.IssuesDataSourceInteractor
import com.dao.issues.data.repository.IssuesRepository
import com.dao.issues.model.Issue
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import junit.extensions.TestSetup
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import io.reactivex.Scheduler


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
    private lateinit var repository: IssuesDataSourceInteractor

    private var testScheduler: Scheduler = TestScheduler()
    private lateinit var presenter: IssuesPresenter

    @Before
    fun setUp()
    {
        MockitoAnnotations.initMocks(this)
        presenter = IssuesPresenter(mock(IssuesRepository::class.java), testScheduler, testScheduler)
        presenter.initialize(view)
    }

    @Test
    fun initialize()
    {
        verify(view).initializeView()
    }

    @Test
    fun loadIssuesList()
    {
        doReturn(Observable.just(listOf<Issue>())).`when`(repository).loadIssues()

        presenter.loadIssuesList()
        verify(view).loadingIssuesList(listOf())
    }
}