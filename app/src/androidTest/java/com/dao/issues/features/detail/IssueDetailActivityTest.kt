package com.dao.issues.features.detail

import android.content.Intent
import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.dao.issues.Extras.ISSUE
import com.dao.issues.GithubApi
import com.dao.issues.KotlinApplication
import com.dao.issues.R
import com.dao.issues.data.IssueFactory
import com.dao.issues.di.DaggerTestAppComponent
import com.dao.issues.util.ResponseBodyParser
import com.dao.issues.util.ToolbarMatcher
import dagger.android.AndroidInjector
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.hamcrest.CoreMatchers.*
import org.junit.*
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
    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    private val server: MockWebServer = MockWebServer()
    private val issue = IssueFactory.buildJson(context)
    private val intent = Intent(context, IssueDetailActivity::class.java).putExtra(ISSUE, issue)

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

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
    fun putOnForm()
    {
        putOnFromIssueComplete(false)

        val scenario = launchActivity<IssueDetailActivity>(intent)
        scenario.moveToState(Lifecycle.State.RESUMED)

        val title = context.getString(R.string.issue_number, issue.number)

        onView(withId(R.id.toolbar)).check(matches(ToolbarMatcher(`is`(title))))
        onView(withId(R.id.message_empty)).check(matches(not(isDisplayed())))

        scenario.close()
    }

    @Test
    fun openInBrowser()
    {
        putOnFromIssueComplete(false)
        val issue = IssueFactory.buildJson(context)

        val scenario = launchActivity<IssueDetailActivity>(intent)
        scenario.moveToState(Lifecycle.State.RESUMED)

        Intents.init()
        onView(withId(R.id.button_github)).perform(click())
        Intents.intended(allOf(hasAction(Intent.ACTION_VIEW), hasData(Uri.parse(issue.url))))
        Intents.release()

        scenario.close()
    }

    @Test
    fun showUserProfile()
    {
        putOnFromIssueComplete(true)

        val scenario = launchActivity<IssueDetailActivity>(intent)
        scenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.button_profile)).perform(click())
        onView(withId(R.id.design_bottom_sheet)).check(matches(`is`(isDisplayed())))



        scenario.close()
    }

    @Test
    fun loadingUserProfile()
    {
        putOnFromIssueComplete(true)
        val issue = IssueFactory.buildJson(context)

        val scenario = launchActivity<IssueDetailActivity>(intent)
        scenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.button_profile)).perform(click())

        onView(withId(R.id.bottom_sheet)).check(matches(`is`(isDisplayed())))
        onView(withId(R.id.name)).check(matches(withText(issue.user.name)))
        onView(withId(R.id.blog)).check(matches(withText(issue.user.blog)))
        onView(withId(R.id.bio)).check(matches(withText(issue.user.bio)))
        onView(withId(R.id.location)).check(matches(withText(issue.user.location)))
        onView(withId(R.id.followers)).check(matches(withText(issue.user.followers.toString())))
        onView(withId(R.id.following)).check(matches(withText(issue.user.following.toString())))

        scenario.close()
    }

    @Test
    fun loadingComments()
    {
        putOnFromIssueComplete(false)

        val scenario = launchActivity<IssueDetailActivity>(intent)
        scenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.message_empty)).check(matches(not(`is`(isDisplayed()))))

        scenario.close()
    }

    @Test
    fun loadingComments_empty()
    {
        putOnFromIssueComplete(true)

        val scenario = launchActivity<IssueDetailActivity>(intent)
        scenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.message_empty)).check(matches(`is`(isDisplayed())))

        scenario.close()
    }

    private fun putOnFromIssueComplete(commentsEmpty: Boolean)
    {
        val dispatcher = object : Dispatcher()
        {
            @Throws(InterruptedException::class)
            override fun dispatch(request: RecordedRequest): MockResponse
            {
                return when(request.path)
                {
                    "/repos/JetBrains/kotlin/issues/${issue.number}/comments" ->
                    {
                        val json = if(commentsEmpty) R.raw.response_200_comments_empty else R.raw.response_200_comments

                        MockResponse().setResponseCode(200).setBody(ResponseBodyParser.from(context, json))
                    }
                    "/users/udalov" ->
                    {
                        MockResponse().setResponseCode(200).setBody(ResponseBodyParser.from(context, R.raw.response_200_user))
                    }
                    else ->
                    {
                        MockResponse().setResponseCode(404)
                    }
                }
            }
        }

        server.dispatcher = dispatcher
    }
}