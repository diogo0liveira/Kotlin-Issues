package com.dao.issues.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created in 26/03/19 21:48.
 *
 * @author Diogo Oliveira.
 */
@Parcelize
data class Issue(
        @SerializedName("html_url")
        val url: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("number")
        val number: Int,
        @SerializedName("created_at")
        val created: String,
        @SerializedName("body")
        val body: String,
        @SerializedName("state")
        val state: State,
        @SerializedName("comments")
        val commentsCount: Int,
        @SerializedName("comments_url")
        val commentsUrl: String,
        @SerializedName("user")
        val user: User) : Parcelable
{
    override fun toString(): String
    {
        return title
    }
}