package com.dao.issues.features.issues

import androidx.annotation.StringRes
import com.dao.issues.base.mvp.IPresenter
import com.dao.issues.base.mvp.IView
import com.dao.issues.model.Issue
import com.dao.issues.util.annotation.Duration

/**
 * Created in 26/03/19 21:44.
 *
 * @author Diogo Oliveira.
 */
interface IssuesInteractor
{
    interface View : IView
    {
        fun loadingIssuesList(issues: List<Issue>)

        fun startIssuesDetailActivity(issue: Issue)

        fun executeRequireNetwork(block: () -> Unit)

        fun toast(@StringRes message: Int, @Duration duration: Int)
    }

    interface Presenter : IPresenter<View>
    {
        fun loadIssuesList()
    }
}