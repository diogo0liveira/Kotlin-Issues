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
import com.dao.issues.KotlinApplication
import com.dao.issues.R
import com.dao.issues.di.DaggerTestAppComponent
import dagger.android.AndroidInjector
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
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

    private val webServer: MockWebServer = MockWebServer()

    init
    {
        webServer.start(8080)

        val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as KotlinApplication
        val builder: AndroidInjector.Factory<KotlinApplication> = DaggerTestAppComponent.factory()
        app.injector = builder.create(app)
        app.injector.inject(app)
    }

    @After
    @Throws(Exception::class)
    fun tearDown()
    {
        webServer.shutdown()
    }

    @Test
    fun onIssueViewOnClick()
    {
        val mockResponse = MockResponse()
                .setBody("{}")
                .setResponseCode(200)
        webServer.enqueue(mockResponse)

        val scenario = launchActivity<IssuesActivity>().apply { moveToState(Lifecycle.State.RESUMED) }

        onView(withId(R.id.issues_list))
                .perform(actionOnItemAtPosition<IssuesAdapter.ViewHolder>(0, click()))

        onView(withId(R.id.content_detail)).check(matches(isDisplayed()))

        scenario.close()
    }
}