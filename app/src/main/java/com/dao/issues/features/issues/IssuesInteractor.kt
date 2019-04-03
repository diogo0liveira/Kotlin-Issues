package com.dao.issues.features.issues

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.dao.issues.base.mvp.IPresenter
import com.dao.issues.base.mvp.IView
import com.dao.issues.model.Issue
import com.dao.issues.network.NetworkState
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
        fun startIssuesDetailActivity(issue: Issue)

        fun executeRequireNetwork(block: () -> Unit)

        fun toast(@StringRes message: Int, @Duration duration: Int)
    }

    interface Presenter : IPresenter<View>
    {
        fun loadIssuesList()

        fun issuesObserver(): LiveData<PagedList<Issue>>

        fun loadIssues(): LiveData<NetworkState>

        fun getNetworkState(): LiveData<NetworkState>
    }
}