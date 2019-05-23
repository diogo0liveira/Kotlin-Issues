package com.dao.issues.data

import com.dao.issues.model.Issue
import com.dao.issues.model.State
import com.dao.issues.model.User
import java.util.*

/**
 * Created in 23/05/19 14:55.
 *
 * @author Diogo Oliveira.
 */
object IssueFactory
{
    fun build(
            url: String = "",
            title: String = "",
            number: Int = 0,
            created: Date = Date(),
            body: String = "",
            state: State = State.ALL,
            commentsCount: Int = 0,
            commentsUrl: String = "",
            user: User = UserFactory.build(0)): Issue
    {
        return Issue(url, title, number, created, body, state, commentsCount, commentsUrl, user)
    }
}