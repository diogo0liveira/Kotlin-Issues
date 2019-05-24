package com.dao.issues.data

import android.content.Context
import com.dao.issues.R
import com.dao.issues.model.Issue
import com.dao.issues.model.State
import com.dao.issues.model.User
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
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


    fun buildJson(context: Context): Issue
    {
        val json = context.resources.openRawResource(R.raw.response_200_issue)
                .reader().readLines().joinToString("") { it.trim() }

        val moshi =  Moshi.Builder()
                .add(Date::class.java, Rfc3339DateJsonAdapter())
                .add(KotlinJsonAdapterFactory()).build()

        val adapter = moshi.adapter<Issue>(Issue::class.java)
        return adapter.fromJson(json)!!
    }
}