package com.dao.issues.features.issues

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.dao.issues.GithubApi
import com.dao.issues.KotlinApplication
import com.dao.issues.R
import com.dao.issues.di.DaggerTestAppComponent
import com.dao.issues.util.ResponseBodyParser
import dagger.android.AndroidInjector
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

/**
 * Created in 29/03/19 11:32.
 *
 * @author Diogo Oliveira.
 */
@LargeTest
@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class IssuesActivityTest
{
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val server: MockWebServer = MockWebServer()

    init
    {
        GithubApi.URL = "http://localhost:8080"

        val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as KotlinApplication
        val builder: AndroidInjector.Factory<KotlinApplication> = DaggerTestAppComponent.factory()
        app.injector = builder.create(app)
        app.injector.inject(app)
    }

    @Before
    fun setUp()
    {
        server.start(8080)
    }

    @After
    @Throws(Exception::class)
    fun tearDown()
    {
        server.shutdown()
    }

    @Test
    fun onIssueViewOnClick()
    {
        var body = ResponseBodyParser.from(context, R.raw.response_200_issues)
        var mockResponse = MockResponse().setBody(body).setResponseCode(200)
        server.enqueue(mockResponse)

        body = ResponseBodyParser.from(context, R.raw.response_200_comments_empty)
        mockResponse = MockResponse().setBody(body).setResponseCode(200)
        server.enqueue(mockResponse)

        val scenario = launchActivity<IssuesActivity>().apply { moveToState(Lifecycle.State.RESUMED) }

        onView(withId(R.id.issues_list))
                .perform(actionOnItemAtPosition<IssuesAdapter.ViewHolder>(0, click()))

        onView(withId(R.id.content_detail)).check(matches(isDisplayed()))

        scenario.close()
    }

    @Test
    fun onListEmpty()
    {
        val body = ResponseBodyParser.from(context, R.raw.response_200_issues_empty)
        val mockResponse = MockResponse().setBody(body).setResponseCode(200)
        server.enqueue(mockResponse)

        val scenario = launchActivity<IssuesActivity>().apply { moveToState(Lifecycle.State.RESUMED) }

        onView(withId(R.id.message_empty)).check(matches(isDisplayed()))

        scenario.close()
    }
}