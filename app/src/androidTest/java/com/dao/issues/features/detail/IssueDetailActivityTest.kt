package com.dao.issues.features.detail

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.dao.issues.rx2idlerktx.Rx2IdlerKtx
import io.reactivex.plugins.RxJavaPlugins
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

/**
 * Created in 05/04/19 12:20.
 *
 * @author Diogo Oliveira.
 */
@LargeTest
@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class IssueDetailActivityTest
{
    private lateinit var mockServer: MockWebServer

    init
    {
        RxJavaPlugins.setInitIoSchedulerHandler(Rx2IdlerKtx.create("RxJava2-IOScheduler"))
    }

    @Before
    fun setUp()
    {
        mockServer = MockWebServer()
        mockServer.start()
    }

    @After
    @Throws
    fun tearDown()
    {
        mockServer.shutdown()
    }

    @Test
    fun putOnForm()
    {
    }

    @Test
    fun openInBrowser()
    {
    }

    @Test
    fun loadingUserProfile()
    {
    }

    @Test
    fun loadingComments()
    {
    }
}