package com.dao.issues.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * Created in 26/03/19 21:48.
 *
 * @author Diogo Oliveira.
 */
@Parcelize
@JsonClass(generateAdapter = true)
data class Issue(
        @Json(name = "html_url")
        val url: String,
        @Json(name = "title")
        val title: String,
        @Json(name = "number")
        val number: Int,
        @Json(name = "created_at")
        val created: Date,
        @Json(name = "body")
        val body: String,
        @Json(name = "state")
        val state: State,
        @Json(name = "comments")
        val commentsCount: Int,
        @Json(name = "comments_url")
        val commentsUrl: String,
        @Json(name = "user")
        val user: User) : Parcelable
{
    override fun toString(): String
    {
        return title
    }
}