package com.dao.issues.features.issues

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
import com.dao.issues.R
import com.dao.issues.rx2idlerktx.Rx2IdlerKtx
import io.reactivex.plugins.RxJavaPlugins
import org.junit.FixMethodOrder
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
    init
    {
        RxJavaPlugins.setInitIoSchedulerHandler(Rx2IdlerKtx.create("RxJava2-IOScheduler"))
    }

    @Test
    fun onIssueViewOnClick()
    {
        val scenario = launchActivity<IssuesActivity>().apply { moveToState(Lifecycle.State.RESUMED) }

        onView(withId(R.id.issues_list))
                .perform(actionOnItemAtPosition<IssuesAdapter.ViewHolder>(0, click()))

        onView(withId(R.id.content_detail)).check(matches(isDisplayed()))

        scenario.close()
    }
}