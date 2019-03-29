package com.dao.issues.data.repository

import com.dao.issues.data.IssuesRepositoryInteractor
import com.dao.issues.data.remote.IssuesRemoteRepository
import com.dao.issues.util.any
import com.dao.issues.util.mock
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.anyString
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created in 29/03/19 15:01.
 *
 * @author Diogo Oliveira.
 */
@RunWith(MockitoJUnitRunner::class)
class IssuesRepositoryTest
{
    @Mock
    private lateinit var remote: IssuesRemoteRepository
    private lateinit var repository: IssuesRepositoryInteractor

    @Before
    fun setUp()
    {
        repository = IssuesRepository(remote)
    }

    @Test
    fun `load user`()
    {
        given(remote.loadUser(anyString())).willReturn(Observable.just(mock()))
        repository.loadUser("").test()
        verify(remote).loadUser(any())
    }

    @Test
    fun `load issues`()
    {
        given(remote.loadIssues()).willReturn(Observable.just(mock()))
        repository.loadIssues().test()
        verify(remote).loadIssues()
    }

    @Test
    fun `load issue comments`()
    {
        given(remote.loadIssueComments(anyString())).willReturn(Observable.just(mock()))
        repository.loadIssueComments("").test()
        verify(remote).loadIssueComments(any())
    }
}