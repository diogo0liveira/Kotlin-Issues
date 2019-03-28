package com.dao.issues.features.detail

import androidx.annotation.StringRes
import com.dao.issues.base.mvp.IPresenter
import com.dao.issues.base.mvp.IView
import com.dao.issues.model.Comment
import com.dao.issues.model.Issue
import com.dao.issues.model.User
import com.dao.issues.util.annotation.Duration

/**
 * Created in 27/03/19 10:59.
 *
 * @author Diogo Oliveira.
 */
interface IssueDetailInteractor
{
    interface View : IView
    {
        fun putOnForm(issue: Issue)

        fun loadingUserProfile(user: User)

        fun loadingComments(comments: List<Comment>)

        fun toast(@StringRes message: Int, @Duration duration: Int)
    }

    interface Presenter : IPresenter<View>
    {
        fun loadIssue(issue: Issue)

        fun loadComments(issue: Issue)

        fun loadUserProfile(user: User)
    }
}