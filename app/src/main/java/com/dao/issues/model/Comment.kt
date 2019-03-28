package com.dao.issues.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created in 27/03/19 22:42.
 *
 * @author Diogo Oliveira.
 */
@Parcelize
data class Comment(
        @SerializedName("created_at")
        val created: String,
        @SerializedName("author_association")
        val author: String,
        @SerializedName("body")
        val body: String,
        @SerializedName("user")
        val user: User) : Parcelable
{
    override fun toString(): String
    {
        return body
    }
}