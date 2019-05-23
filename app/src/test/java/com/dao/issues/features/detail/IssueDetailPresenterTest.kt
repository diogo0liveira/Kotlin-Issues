package com.dao.issues.features.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dao.issues.data.IssueFactory
import com.dao.issues.data.IssuesRepositoryInteractor
import com.dao.issues.data.UserFactory
import com.dao.issues.model.Comment
import com.dao.issues.model.User
import com.dao.issues.util.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

/**
 * Created in 29/03/19 16:08.
 *
 * @author Diogo Oliveira.
 */
class IssueDetailPresenterTest
{
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var view: IssueDetailInteractor.View
    @Mock
    private lateinit var repository: IssuesRepositoryInteractor

    private val schedulerProvider = SchedulerProvider(Schedulers.trampoline(), Schedulers.trampoline())
    private lateinit var presenter: IssueDetailPresenter

    private val user= UserFactory.build(0)
    private val issue = IssueFactory.build("")

    @Before
    fun setUp()
    {
        MockitoAnnotations.initMocks(this)
        presenter = IssueDetailPresenter(repository, schedulerProvider)
        presenter.initialize(view)
    }

    @Test
    fun initialize()
    {
        verify(view).initializeView()
    }

    @Test
    fun `load issue`()
    {
        presenter.loadIssue(issue)
        verify(view).putOnForm(issue)
    }

    @Test
    fun `show issues github`()
    {
        presenter.loadIssue(issue)
        presenter.showIssuesGithub()
        verify(view).openInBrowser(issue.url)
    }

    @Test
    fun `load user profile`()
    {
        `when`(repository.loadUser(anyString()))
                .thenReturn(Observable.just<User>(user))

        presenter.loadIssue(issue)
        presenter.loadUserProfile()
        verify(view).loadingUserProfile(user)
    }

    @Test
    fun `load comments`()
    {
        val comments = listOf<Comment>()
        `when`(repository.loadIssueComments(anyString()))
                .thenReturn(Observable.just<List<Comment>>(comments))

        presenter.loadIssue(issue)
        presenter.loadComments()
        verify(view).loadingComments(comments)
    }
}