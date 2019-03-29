package com.dao.issues.features.issues

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.dao.issues.R
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
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
    val intentRule = IntentsTestRule(IssuesActivity::class.java, false)

    init
    {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    }

    @Before
    fun setUp()
    {
        intentRule.launchActivity(null)
    }

    @Test
    fun onIssueViewOnClick()
    {
        onView(ViewMatchers.withId(R.id.issues_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition<IssuesAdapter.ViewHolder>(0, ViewActions.click()))

        onView(ViewMatchers.withId(R.id.anchor)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun showLoading()
    {

    }

    @Test
    fun startIssuesDetailActivity()
    {
    }

    @Test
    fun executeRequireNetwork()
    {
    }
}